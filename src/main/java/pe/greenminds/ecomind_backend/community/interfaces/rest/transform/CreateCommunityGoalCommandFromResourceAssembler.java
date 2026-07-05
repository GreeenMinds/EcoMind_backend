package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateCommunityGoalResource;

public class CreateCommunityGoalCommandFromResourceAssembler {

    private CreateCommunityGoalCommandFromResourceAssembler() {}

    public static CreateCommunityGoalCommand toCommandFromResource(CreateCommunityGoalResource resource) {
        return new CreateCommunityGoalCommand(
                resource.communityId(),
                resource.goalId(),
                resource.description(),
                resource.target()
        );
    }
}
