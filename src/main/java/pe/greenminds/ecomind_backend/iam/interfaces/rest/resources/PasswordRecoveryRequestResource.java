package pe.greenminds.ecomind_backend.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordRecoveryRequestResource(@NotBlank @Email String email) {
}
