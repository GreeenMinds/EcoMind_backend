package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(
        name = "CreateCommunityGoalRequest",
        description = "Request payload for creating a community goal",
        example = """
        {
          "community_id": 1,
          "goal_id": 2,
          "description": "Complete 20 quests from the Energy category.",
          "target": 20
        }
        """
)
public record CreateCommunityGoalResource(
        @NotNull(message = "is required")
        @JsonProperty("community_id")
        @Schema(description = "Community identifier", example = "1")
        Long communityId,

        @NotNull(message = "is required")
        @JsonProperty("goal_id")
        @Schema(description = "Goal catalog identifier", example = "2")
        Long goalId,

        @NotBlank(message = "is required")
        @Schema(description = "Community goal description", example = "Complete 20 quests from the Energy category.")
        String description,

        @NotNull(message = "is required")
        @Positive(message = "must be greater than zero")
        @Schema(description = "Target amount required to complete the goal", example = "20")
        Integer target
) {
}
