package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityUserResource;

public class UpdateActivityUserCommandFromResourceAssembler {
    private UpdateActivityUserCommandFromResourceAssembler() {}

    public static UpdateActivityUserCommand toCommandFromResource(Long id, ActivityUserResource resource) {
        return new UpdateActivityUserCommand(
                id, resource.userId(), resource.activityId(),
                resource.progress(), resource.endDate(),
                resource.collaborativeSessionId()
        );
    }
}
