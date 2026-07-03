package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import java.util.List;

public record UpdateFamilyPlanCommand(
        Long familyPlanId,
        List<FamilyPlanItemCommand> items
) {
}
