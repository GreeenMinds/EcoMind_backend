package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdateCommunityGoalResource;

public class UpdateCommunityGoalCommandFromResourceAssembler {

    private UpdateCommunityGoalCommandFromResourceAssembler() {}

    public static UpdateCommunityGoalCommand toCommandFromResource(Long id, UpdateCommunityGoalResource resource) {
        return new UpdateCommunityGoalCommand(
                id,
                resource.description(),
                resource.target(),
                resource.progress(),
                resource.participants(),
                CommunityGoalStatus.fromValue(resource.status())
        );
    }
}
