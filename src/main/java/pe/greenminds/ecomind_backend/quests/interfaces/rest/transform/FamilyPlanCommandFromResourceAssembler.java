package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateFamilyPlanCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.FamilyPlanItemCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateFamilyPlanCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateFamilyPlanResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.FamilyPlanItemRequestResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.UpdateFamilyPlanResource;

public final class FamilyPlanCommandFromResourceAssembler {
    private FamilyPlanCommandFromResourceAssembler() {
    }

    public static CreateFamilyPlanCommand toCommandFromResource(
            CreateFamilyPlanResource resource
    ) {
        return new CreateFamilyPlanCommand(
                resource.familyId(),
                resource.ownerUserId(),
                resource.items().stream()
                        .map(FamilyPlanCommandFromResourceAssembler::toItemCommand)
                        .toList()
        );
    }

    public static UpdateFamilyPlanCommand toCommandFromResource(
            Long familyPlanId,
            UpdateFamilyPlanResource resource
    ) {
        return new UpdateFamilyPlanCommand(
                familyPlanId,
                resource.items().stream()
                        .map(FamilyPlanCommandFromResourceAssembler::toItemCommand)
                        .toList()
        );
    }

    private static FamilyPlanItemCommand toItemCommand(
            FamilyPlanItemRequestResource resource
    ) {
        return new FamilyPlanItemCommand(resource.questId(), resource.startDate());
    }
}
