package pe.greenminds.ecomind_backend.quests.domain.model.queries;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;

public record GetQuestUsersByUserIdAndStatusQuery(
        Long userId,
        QuestStatus status
) {
}
