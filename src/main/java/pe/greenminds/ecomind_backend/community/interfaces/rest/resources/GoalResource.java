package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "GoalResponse",
        description = "Community goal catalog information response",
        example = """
        {
          "id": 1,
          "title": "Save energy at home together",
          "unit": "quests",
          "quest_category": "energy"
        }
        """
)
public record GoalResource(
        @Schema(description = "Goal unique identifier", example = "1")
        Long id,

        @Schema(description = "Goal title", example = "Save energy at home together")
        String title,

        @Schema(description = "Goal measurement unit", example = "quests")
        String unit,

        @JsonProperty("quest_category")
        @Schema(description = "Quest category linked to this goal", example = "energy")
        String questCategory
) {
}