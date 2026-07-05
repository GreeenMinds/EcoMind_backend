package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.MinigameAttemptStatus;

import java.time.OffsetDateTime;
import java.util.Map;

public record MinigameAttemptResource(
        Long id,
        Long userId,
        Long questId,
        Integer score,
        MinigameAttemptStatus status,
        OffsetDateTime startDate,
        OffsetDateTime endDate,
        Map<String, Object> metadata,
        Integer givenGems,
        Integer givenEcopoints
) {
}
