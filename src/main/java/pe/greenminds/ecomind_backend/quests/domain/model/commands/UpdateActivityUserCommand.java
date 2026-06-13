package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import java.time.LocalDateTime;

public record UpdateActivityUserCommand(
        Long id, Long userId, Long activityId, Integer progress,
        LocalDateTime endDate, Long collaborativeSessionId
) {
}
