package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateMinigameCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateMinigameResource;

public final class CreateMinigameCommandFromResourceAssembler {
    private CreateMinigameCommandFromResourceAssembler() {
    }

    public static CreateMinigameCommand toCommandFromResource(CreateMinigameResource resource) {
        return new CreateMinigameCommand(
                resource.name(),
                resource.description(),
                resource.url(),
                resource.completionRules()
        );
    }
}
