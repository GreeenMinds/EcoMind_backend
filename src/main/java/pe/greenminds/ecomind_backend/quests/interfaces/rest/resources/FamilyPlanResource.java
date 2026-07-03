package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;

import java.util.List;

public record FamilyPlanResource(
        Long id,
        Long familyId,
        Long ownerUserId,
        FamilyPlanStatus status,
        Double progress,
        List<FamilyPlanItemResource> items
) {
}
