package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.Map;

public record FinishMinigameAttemptResource(
        @NotNull
        @PositiveOrZero
        Integer score,

        Map<String, Object> metadata
) {
}
