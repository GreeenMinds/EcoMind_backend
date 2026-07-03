package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.FinishMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.FinishMinigameAttemptResource;

public final class FinishMinigameAttemptCommandFromResourceAssembler {
    private FinishMinigameAttemptCommandFromResourceAssembler() {
    }

    public static FinishMinigameAttemptCommand toCommandFromResource(
            Long attemptId,
            FinishMinigameAttemptResource resource
    ) {
        return new FinishMinigameAttemptCommand(
                attemptId,
                resource.score(),
                resource.metadata()
        );
    }
}
