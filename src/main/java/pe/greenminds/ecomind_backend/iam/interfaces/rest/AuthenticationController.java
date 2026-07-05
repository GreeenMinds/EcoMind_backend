package pe.greenminds.ecomind_backend.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.iam.application.CurrentAuthenticatedUserService;
import pe.greenminds.ecomind_backend.iam.application.commandservices.AuthenticationCommandService;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.*;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.UserResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.ErrorResource;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Identity and access management endpoints")
public class AuthenticationController {
    private final AuthenticationCommandService authenticationCommandService;
    private final CurrentAuthenticatedUserService currentAuthenticatedUserService;

    public AuthenticationController(AuthenticationCommandService authenticationCommandService,
                                    CurrentAuthenticatedUserService currentAuthenticatedUserService) {
        this.authenticationCommandService = authenticationCommandService;
        this.currentAuthenticatedUserService = currentAuthenticatedUserService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                authenticationCommandService.signUp(resource),
                AuthenticatedUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                authenticationCommandService.signIn(resource),
                AuthenticatedUserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return currentAuthenticatedUserService.currentUserId()
                .<ResponseEntity<?>>map(userId -> ResponseEntityAssembler.toResponseEntityFromResult(
                        authenticationCommandService.me(userId),
                        UserResourceFromEntityAssembler::toResourceFromEntity,
                        HttpStatus.OK
                ))
                .orElseGet(this::unauthorized);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<?> deleteAccount() {
        return currentAuthenticatedUserService.currentUserId()
                .<ResponseEntity<?>>map(userId -> ResponseEntityAssembler.toResponseEntityFromResult(
                        authenticationCommandService.deleteAccount(userId),
                        ignored -> null,
                        HttpStatus.NO_CONTENT
                ))
                .orElseGet(this::unauthorized);
    }

    @PostMapping("/password-recovery/request")
    public ResponseEntity<?> requestPasswordRecovery(@Valid @RequestBody PasswordRecoveryRequestResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                authenticationCommandService.requestPasswordRecovery(resource.email()),
                email -> new PasswordRecoveryResponseResource(email, true),
                HttpStatus.OK
        );
    }

    @PostMapping("/password-recovery/confirm")
    public ResponseEntity<?> confirmPasswordRecovery(@Valid @RequestBody PasswordRecoveryConfirmResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                authenticationCommandService.confirmPasswordRecovery(resource.token(), resource.newPassword()),
                ignored -> null,
                HttpStatus.NO_CONTENT
        );
    }

    private ResponseEntity<ErrorResource> unauthorized() {
        var error = new ApplicationError("UNAUTHORIZED", "Authentication is required");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResource(error.code(), error.message(), error.details()));
    }
}
