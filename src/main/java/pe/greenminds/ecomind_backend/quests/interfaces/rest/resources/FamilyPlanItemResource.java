package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import java.time.OffsetDateTime;

public record FamilyPlanItemResource(
        Long id,
        Long questId,
        OffsetDateTime startDate,
        Long collaborativeSessionId,
        Double progress
) {
}
