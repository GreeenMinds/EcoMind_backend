package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record CompletedQuestResource(
        String questTitle,
        String completedAt,
        Integer ecopoints,
        Integer activityCount
) {}
