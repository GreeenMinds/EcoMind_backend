package pe.greenminds.ecomind_backend.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpResource(
        @NotBlank @Size(min = 2, max = 120) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 120) String password,
        @NotNull Long communityId,
        String birthDate,
        @Size(max = 1000) String commitment
) {
}
