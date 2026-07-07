package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import java.util.Map;

public record FinishMinigameAttemptCommand(
        Long attemptId,
        Integer score,
        Map<String, Object> metadata
) {
}
