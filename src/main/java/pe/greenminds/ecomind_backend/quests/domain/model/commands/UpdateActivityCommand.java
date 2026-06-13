package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

public record UpdateActivityCommand(
        Long activityId, Long questId, String description,
        Integer position, ActivityType type, String image
) {
}
