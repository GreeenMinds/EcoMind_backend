package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestUserStatus;

import java.time.LocalDateTime;

public record QuestUserResource(
        Long id,
        Long userId,
        Long questId,
        QuestUserStatus status,
        Integer progress,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long collaborativeSessionId
) {
}
