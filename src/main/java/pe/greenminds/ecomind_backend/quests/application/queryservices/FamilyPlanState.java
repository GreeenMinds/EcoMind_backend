package pe.greenminds.ecomind_backend.quests.application.queryservices;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;

import java.util.List;

public record FamilyPlanState(
        Long id,
        Long familyId,
        Long ownerUserId,
        FamilyPlanStatus status,
        Double progress,
        List<FamilyPlanItemState> items
) {
}
