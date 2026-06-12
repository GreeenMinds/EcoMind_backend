package pe.greenminds.ecomind_backend.quests.domain.model.events;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;

public record QuestCreatedEvent(
        Long questId,
        Long minimageId,
        Category category,
        String title,
        String description,
        String image,
        QuestType type,
        Integer reward_gems,
        Integer reward_ecopoints,
        Integer time,
        String theme
) {
    public static QuestCreatedEvent from(Quest quest) {
        var reward = quest.getReward();
        return new QuestCreatedEvent(
                quest.getId(),
                quest.getMinigameId(),
                quest.getCategory(),
                quest.getTitle(),
                quest.getDescription(),
                quest.getImage(),
                quest.getType(),
                reward.gems(),
                reward.ecopoints(),
                quest.getTime(),
                quest.getTheme()
        )
    }
}
