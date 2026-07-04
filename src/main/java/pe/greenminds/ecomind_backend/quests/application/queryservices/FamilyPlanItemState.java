package pe.greenminds.ecomind_backend.quests.application.queryservices;

public record FamilyPlanItemState(
        Long id,
        Long questId,
        Long collaborativeSessionId,
        Double progress
) {
}
