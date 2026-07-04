package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
        name = "UpdateCommunityGoalRequest",
        description = "Request payload for updating a community goal",
        example = """
        {
          "description": "Complete 20 quests from the Energy category.",
          "target": 20,
          "progress": 4,
          "participants": 1,
          "status": "active"
        }
        """
)
public record UpdateCommunityGoalResource(
        @NotBlank(message = "is required")
        @Schema(description = "Community goal description", example = "Complete 20 quests from the Energy category.")
        String description,

        @NotNull(message = "is required")
        @Positive(message = "must be greater than zero")
        @Schema(description = "Target amount required to complete the goal", example = "20")
        Integer target,

        @NotNull(message = "is required")
        @PositiveOrZero(message = "must be positive or zero")
        @Schema(description = "Current goal progress", example = "4")
        Integer progress,

        @NotNull(message = "is required")
        @PositiveOrZero(message = "must be positive or zero")
        @Schema(description = "Number of participants contributing to the goal", example = "1")
        Integer participants,

        @NotBlank(message = "is required")
        @Schema(description = "Community goal status", example = "active", allowableValues = {"active", "completed"})
        String status
) {
}
