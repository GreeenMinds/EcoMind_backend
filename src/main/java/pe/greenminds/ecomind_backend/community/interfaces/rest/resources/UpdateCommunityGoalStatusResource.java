package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "UpdateCommunityGoalStatusRequest",
        description = "Request payload for changing community goal status",
        example = """
        {
          "status": "completed"
        }
        """
)
public record UpdateCommunityGoalStatusResource(
        @NotBlank(message = "is required")
        @Schema(description = "Community goal status", example = "completed", allowableValues = {"active", "completed"})
        String status
) {
}
