package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateCommunityGoalStatusCommand;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdateCommunityGoalStatusResource;

public class UpdateCommunityGoalStatusCommandFromResourceAssembler {

    private UpdateCommunityGoalStatusCommandFromResourceAssembler() {}

    public static UpdateCommunityGoalStatusCommand toCommandFromResource(Long id, UpdateCommunityGoalStatusResource resource) {
        return new UpdateCommunityGoalStatusCommand(
                id,
                CommunityGoalStatus.fromValue(resource.status())
        );
    }
}
