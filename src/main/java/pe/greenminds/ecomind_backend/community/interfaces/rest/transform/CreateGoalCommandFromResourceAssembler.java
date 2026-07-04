package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateGoalCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateGoalResource;

public class CreateGoalCommandFromResourceAssembler {

    private CreateGoalCommandFromResourceAssembler() {}

    public static CreateGoalCommand toCommandFromResource(CreateGoalResource resource) {
        return new CreateGoalCommand(
                resource.title(),
                resource.unit(),
                resource.questCategory()
        );
    }
}