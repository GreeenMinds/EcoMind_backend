package pe.greenminds.ecomind_backend.quests.domain.model.commands;

import java.util.Map;

public record CreateMinigameCommand(
        String name,
        String description,
        String url,
        Map<String, Object> completionRules
) {
}
