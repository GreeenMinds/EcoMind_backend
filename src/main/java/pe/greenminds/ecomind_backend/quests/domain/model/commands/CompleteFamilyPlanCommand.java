package pe.greenminds.ecomind_backend.quests.domain.model.commands;

public record CompleteFamilyPlanCommand(
        Long familyPlanId,
        Long ownerUserId
) {
}
