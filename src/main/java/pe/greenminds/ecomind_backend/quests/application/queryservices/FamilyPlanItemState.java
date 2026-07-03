package pe.greenminds.ecomind_backend.quests.application.queryservices;

import java.time.OffsetDateTime;

public record FamilyPlanItemState(
        Long id,
        Long questId,
        OffsetDateTime startDate,
        Long collaborativeSessionId,
        Double progress
) {
}
