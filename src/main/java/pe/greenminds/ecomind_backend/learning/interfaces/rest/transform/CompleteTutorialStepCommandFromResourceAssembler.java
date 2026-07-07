package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.commands.CompleteTutorialStepCommand;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.CompleteTutorialStepResource;

public class CompleteTutorialStepCommandFromResourceAssembler {

    private CompleteTutorialStepCommandFromResourceAssembler() {}

    public static CompleteTutorialStepCommand toCommandFromResource(
            CompleteTutorialStepResource resource
    ) {
        return new CompleteTutorialStepCommand(resource.userId());
    }
}
