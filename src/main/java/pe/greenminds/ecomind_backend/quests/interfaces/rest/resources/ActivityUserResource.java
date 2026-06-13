package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import java.time.LocalDateTime;

public record ActivityUserResource(
        Long id,
        Long userId,
        Long activityId,
        Integer progress,
        LocalDateTime endDate,
        Long collaborativeSessionId
) {
}
