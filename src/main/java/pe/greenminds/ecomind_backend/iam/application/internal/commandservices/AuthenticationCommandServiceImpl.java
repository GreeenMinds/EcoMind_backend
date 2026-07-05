package pe.greenminds.ecomind_backend.iam.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.iam.application.commandservices.AuthenticationCommandService;
import pe.greenminds.ecomind_backend.iam.application.outboundservices.TokenService;
import pe.greenminds.ecomind_backend.iam.domain.model.AuthenticatedUser;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.entities.AccountCredentialPersistenceEntity;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.entities.PasswordResetTokenPersistenceEntity;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories.AccountCredentialPersistenceRepository;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories.PasswordResetTokenPersistenceRepository;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.SignInResource;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.SignUpResource;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityRepository;
import pe.greenminds.ecomind_backend.profile.application.commandservices.UserCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteUserCommand;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthenticationCommandServiceImpl implements AuthenticationCommandService {
    private final UserRepository userRepository;
    private final UserCommandService userCommandService;
    private final CommunityRepository communityRepository;
    private final AccountCredentialPersistenceRepository credentialRepository;
    private final PasswordResetTokenPersistenceRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final long passwordResetExpirationMinutes;

    public AuthenticationCommandServiceImpl(UserRepository userRepository,
                                            UserCommandService userCommandService,
                                            CommunityRepository communityRepository,
                                            AccountCredentialPersistenceRepository credentialRepository,
                                            PasswordResetTokenPersistenceRepository passwordResetTokenRepository,
                                            PasswordEncoder passwordEncoder,
                                            TokenService tokenService,
                                            @Value("${authorization.password-reset.expiration.minutes}") long passwordResetExpirationMinutes) {
        this.userRepository = userRepository;
        this.userCommandService = userCommandService;
        this.communityRepository = communityRepository;
        this.credentialRepository = credentialRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.passwordResetExpirationMinutes = passwordResetExpirationMinutes;
    }

    @Override
    @Transactional
    public Result<AuthenticatedUser, ApplicationError> signUp(SignUpResource resource) {
        var email = normalizeEmail(resource.email());
        if (userRepository.existsByEmail(email) || credentialRepository.existsByEmail(email)) {
            return Result.failure(ApplicationError.conflict("User", "Email already exists"));
        }

        if (communityRepository.findById(resource.communityId()).isEmpty()) {
            return Result.failure(ApplicationError.notFound("Community", resource.communityId().toString()));
        }

        var birthDate = parseBirthDate(resource.birthDate());
        var createdUser = userCommandService.handle(new CreateUserCommand(
                resource.communityId(),
                email,
                birthDate,
                resource.name().trim(),
                0,
                normalizeCommitment(resource.commitment()),
                OffsetDateTime.now(),
                0,
                0,
                null
        ));

        return switch (createdUser) {
            case Result.Success<User, ApplicationError> success -> {
                credentialRepository.save(newCredential(success.value().getId(), email, resource.password()));
                yield authenticated(success.value());
            }
            case Result.Failure<User, ApplicationError> failure -> Result.failure(failure.error());
        };
    }

    @Override
    @Transactional
    public Result<AuthenticatedUser, ApplicationError> signIn(SignInResource resource) {
        var email = normalizeEmail(resource.email());
        var credential = credentialRepository.findByEmail(email);
        if (credential.isEmpty() || !passwordEncoder.matches(resource.password(), credential.get().getPasswordHash())) {
            return Result.failure(ApplicationError.validationError("credentials", "Invalid email or password"));
        }

        var user = userRepository.findById(credential.get().getUserId());
        if (user.isEmpty()) {
            return Result.failure(ApplicationError.notFound("User", credential.get().getUserId().toString()));
        }

        credential.get().setLastLoginAt(OffsetDateTime.now());
        credentialRepository.save(credential.get());
        return authenticated(user.get());
    }

    @Override
    public Result<User, ApplicationError> me(Long userId) {
        return userRepository.findById(userId)
                .<Result<User, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(ApplicationError.notFound("User", userId.toString())));
    }

    @Override
    @Transactional
    public Result<User, ApplicationError> deleteAccount(Long userId) {
        credentialRepository.deleteByUserId(userId);
        return userCommandService.handle(new DeleteUserCommand(userId));
    }

    @Override
    @Transactional
    public Result<String, ApplicationError> requestPasswordRecovery(String email) {
        var normalizedEmail = normalizeEmail(email);
        var user = userRepository.findByEmail(normalizedEmail);
        if (user.isPresent()) {
            var rawToken = UUID.randomUUID().toString();
            var token = new PasswordResetTokenPersistenceEntity();
            token.setUserId(user.get().getId());
            token.setTokenHash(sha256(rawToken));
            token.setExpiresAt(OffsetDateTime.now().plusMinutes(passwordResetExpirationMinutes));
            passwordResetTokenRepository.save(token);
        }
        return Result.success(normalizedEmail);
    }

    @Override
    @Transactional
    public Result<Void, ApplicationError> confirmPasswordRecovery(String token, String newPassword) {
        var resetToken = passwordResetTokenRepository.findByTokenHash(sha256(token));
        if (resetToken.isEmpty() || resetToken.get().getUsedAt() != null ||
                resetToken.get().getExpiresAt().isBefore(OffsetDateTime.now())) {
            return Result.failure(ApplicationError.validationError("token", "Invalid or expired password reset token"));
        }

        var credential = credentialRepository.findByUserId(resetToken.get().getUserId());
        if (credential.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Credentials", resetToken.get().getUserId().toString()));
        }

        credential.get().setPasswordHash(passwordEncoder.encode(newPassword));
        credentialRepository.save(credential.get());
        resetToken.get().setUsedAt(OffsetDateTime.now());
        passwordResetTokenRepository.save(resetToken.get());
        return Result.success(null);
    }

    private Result<AuthenticatedUser, ApplicationError> authenticated(User user) {
        return Result.success(new AuthenticatedUser(user, tokenService.generateToken(user)));
    }

    private AccountCredentialPersistenceEntity newCredential(Long userId, String email, String password) {
        var credential = new AccountCredentialPersistenceEntity();
        credential.setUserId(userId);
        credential.setEmail(email);
        credential.setPasswordHash(passwordEncoder.encode(password));
        return credential;
    }

    private String normalizeEmail(String email) {
        return email == null ? "" : email.trim().toLowerCase();
    }

    private LocalDate parseBirthDate(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return LocalDate.parse(value);
    }

    private String normalizeCommitment(String commitment) {
        if (commitment == null || commitment.isBlank()) {
            return null;
        }
        return commitment.trim();
    }

    private String sha256(String value) {
        try {
            var digest = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(digest.digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is not available", exception);
        }
    }
}
