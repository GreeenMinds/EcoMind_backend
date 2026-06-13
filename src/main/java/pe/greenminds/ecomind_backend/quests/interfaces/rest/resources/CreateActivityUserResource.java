package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateActivityUserResource(
        @NotNull Long userId,
        @NotNull Long activityId
) {
}
