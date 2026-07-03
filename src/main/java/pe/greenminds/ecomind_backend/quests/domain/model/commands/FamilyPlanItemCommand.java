package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import java.time.OffsetDateTime;

public record FamilyPlanItemCommand(
        Long questId,
        OffsetDateTime startDate
) {
}
