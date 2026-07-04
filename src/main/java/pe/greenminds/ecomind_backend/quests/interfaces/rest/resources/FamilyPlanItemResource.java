package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

public record FamilyPlanItemResource(
        Long id,
        Long questId,
        Long collaborativeSessionId,
        Double progress
) {
}
