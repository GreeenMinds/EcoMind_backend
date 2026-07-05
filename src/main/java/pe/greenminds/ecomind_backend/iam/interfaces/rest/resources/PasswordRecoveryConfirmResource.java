package pe.greenminds.ecomind_backend.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordRecoveryConfirmResource(
        @NotBlank String token,
        @NotBlank @Size(min = 6, max = 120) String newPassword
) {
}
