package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;

import java.time.LocalDate;

@Schema(
        name = "QuestResponse",
        description = "Quest information response"
)
public record QuestResource(
        @Schema(description = "Quest unique identifier", example = "1") Long id,
        @Schema(description = "MinigameId for quest with minigame") Long minigameId,
        @Schema(description = "Quest title", example = "Turn off unnecessary lights") String title,
        @Schema(description = "Quest description", example = "Turn off lights that are not being used.") String description,
        @Schema(description = "Quest category", example = "ENERGY") Category category,
        @Schema(description = "Quest type", example = "ACTIVITIES") QuestType type,
        @Schema(description = "Quest gem completion award", example = "30") Integer gemReward,
        @Schema(description = "Quest ecopoints completion award", example = "30") Integer ecopoints,
        @Schema(description = "Age that quest is directed towards", example = "0") Integer age,
        @Schema(description = "Expected time to complete the quest (in minutes)", example = "10") Integer time,
        @Schema(description = "Quest theme card", example = "CHECKBOX") Theme theme,
        @Schema(description = "Assigned date for daily quests", example = "2026-06-12") LocalDate assignedDate,
        @Schema(description = "Expiration date for the quest", example = "2026-06-13") LocalDate expirationDate,
        @Schema(description = "Image url for quest description") String image
) {
}
