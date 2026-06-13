package pe.greenminds.ecomind_backend.quests.domain.model.commands;


import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;

import java.time.LocalDate;

public record CreateQuestCommand (
        Long minigameId,
        Category category,
        String title,
        String description,
        String image,
        QuestType type,
        Integer rewardGems,
        Integer rewardEcopoints,
        Integer time,
        Theme theme,
        Integer age,
        LocalDate assignedDate
) {
}
