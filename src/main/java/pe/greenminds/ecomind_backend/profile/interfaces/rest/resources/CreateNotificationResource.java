package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationResource(
        @JsonProperty("user_id") @NotNull Long userId,
        @NotBlank String type,
        @NotBlank String title,
        @NotBlank String message,
        @JsonProperty("reference_type") String referenceType,
        @JsonProperty("reference_id") Long referenceId
) {
}
