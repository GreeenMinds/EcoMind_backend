package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "CreateGoalRequest",
        description = "Request payload for creating a goal catalog item",
        example = """
        {
          "title": "Save energy at home together",
          "unit": "quests",
          "quest_category": "energy"
        }
        """
)
public record CreateGoalResource(
        @NotBlank(message = "is required")
        @Schema(description = "Goal title", example = "Save energy at home together")
        String title,

        @NotBlank(message = "is required")
        @Schema(description = "Goal measurement unit", example = "quests")
        String unit,

        @NotBlank(message = "is required")
        @JsonProperty("quest_category")
        @Schema(description = "Quest category linked to this goal", example = "energy")
        String questCategory
) {
}