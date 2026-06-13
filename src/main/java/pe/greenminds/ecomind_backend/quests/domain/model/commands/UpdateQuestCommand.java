package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;

import java.time.LocalDate;

public record UpdateQuestCommand(
        Long questId, Long minigameId, String title, String description,
        Category category, QuestType type, Integer rewardGems, Integer rewardEcopoints,
        Integer age, Integer time, String image, Theme theme,
        LocalDate assignedDate, LocalDate expirationDate
) {
}
