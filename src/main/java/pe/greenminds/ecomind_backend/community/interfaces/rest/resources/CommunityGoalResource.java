package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CommunityGoalResponse",
        description = "Community goal information response",
        example = """
        {
          "id": 1,
          "community_id": 1,
          "goal_id": 2,
          "description": "Complete 20 quests from the Energy category.",
          "target": 20,
          "progress": 1,
          "participants": 1,
          "status": "active"
        }
        """
)
public record CommunityGoalResource(
        @Schema(description = "Community goal unique identifier", example = "1")
        Long id,

        @JsonProperty("community_id")
        @Schema(description = "Community identifier", example = "1")
        Long communityId,

        @JsonProperty("goal_id")
        @Schema(description = "Goal catalog identifier", example = "2")
        Long goalId,

        @Schema(description = "Community goal description", example = "Complete 20 quests from the Energy category.")
        String description,

        @Schema(description = "Target amount required to complete the goal", example = "20")
        Integer target,

        @Schema(description = "Current goal progress", example = "1")
        Integer progress,

        @Schema(description = "Number of participants contributing to the goal", example = "1")
        Integer participants,

        @Schema(description = "Community goal status", example = "active", allowableValues = {"active", "completed"})
        String status
) {
}
