package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;

public record CreateQuestUserResource(
        @NotNull Long userId,
        @NotNull Long questId
) {
}
