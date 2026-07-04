package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateGoalCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdateGoalResource;

public class UpdateGoalCommandFromResourceAssembler {

    private UpdateGoalCommandFromResourceAssembler() {}

    public static UpdateGoalCommand toCommandFromResource(Long id, UpdateGoalResource resource) {
        return new UpdateGoalCommand(
                id,
                resource.title(),
                resource.unit(),
                resource.questCategory()
        );
    }
}