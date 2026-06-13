package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestUserStatus;

import java.time.LocalDateTime;

public record UpdateQuestUserCommand(
        Long id, Long userId, Long questId, QuestUserStatus status,
        Integer progress, LocalDateTime endDate, Long collaborativeSessionId
) {
}
