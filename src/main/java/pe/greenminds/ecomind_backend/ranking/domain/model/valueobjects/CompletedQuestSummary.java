package pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects;

import java.time.OffsetDateTime;

public record CompletedQuestSummary(
        String questTitle,
        OffsetDateTime completedAt,
        Integer ecopoints,
        Integer activityCount
) {}
