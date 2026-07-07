package pe.greenminds.ecomind_backend.iam.application.internal.eventhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Community;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityRepository;
import pe.greenminds.ecomind_backend.iam.application.commandservices.AuthenticationCommandService;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.entities.AccountCredentialPersistenceEntity;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories.AccountCredentialPersistenceRepository;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.SignUpResource;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

@Component
public class IamSeedApplicationReadyEventHandler {
    private final CommunityRepository communityRepository;
    private final AuthenticationCommandService authenticationCommandService;
    private final UserRepository userRepository;
    private final AccountCredentialPersistenceRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final boolean enabled;
    private final String email;
    private final String password;
    private final String name;
    private final Long communityId;

    public IamSeedApplicationReadyEventHandler(CommunityRepository communityRepository,
                                              AuthenticationCommandService authenticationCommandService,
                                              UserRepository userRepository,
                                              AccountCredentialPersistenceRepository credentialRepository,
                                              PasswordEncoder passwordEncoder,
                                              @Value("${iam.seed.enabled}") boolean enabled,
                                              @Value("${iam.seed.email}") String email,
                                              @Value("${iam.seed.password}") String password,
                                              @Value("${iam.seed.name}") String name,
                                              @Value("${iam.seed.community-id:1}") Long communityId) {
        this.communityRepository = communityRepository;
        this.authenticationCommandService = authenticationCommandService;
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
        this.passwordEncoder = passwordEncoder;
        this.enabled = enabled;
        this.email = email;
        this.password = password;
        this.name = name;
        this.communityId = communityId;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        if (!enabled) {
            return;
        }
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            var community = communityRepository.findById(communityId)
                    .orElseGet(() -> communityRepository.save(new Community("General", 0, "Global")));
            authenticationCommandService.signUp(new SignUpResource(name, email, password, community.getId(), null, null));
            return;
        }
        if (credentialRepository.findByUserId(user.get().getId()).isPresent()) {
            return;
        }
        var credential = new AccountCredentialPersistenceEntity();
        credential.setUserId(user.get().getId());
        credential.setEmail(email);
        credential.setPasswordHash(passwordEncoder.encode(password));
        credentialRepository.save(credential);
    }
}
