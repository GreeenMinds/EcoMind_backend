package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "IncrementCommunityGoalProgressRequest",
        description = "Request payload for increasing community goal progress",
        example = """
        {
          "increment": 1
        }
        """
)
public record IncrementCommunityGoalProgressResource(
        @NotNull(message = "is required")
        @Schema(description = "Progress increment. Only +1 is allowed.", example = "1")
        Integer increment
) {
}
