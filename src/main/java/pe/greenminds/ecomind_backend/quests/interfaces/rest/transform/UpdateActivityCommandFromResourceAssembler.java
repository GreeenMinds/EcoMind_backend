package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityResource;

public class UpdateActivityCommandFromResourceAssembler {
    private UpdateActivityCommandFromResourceAssembler() {}

    public static UpdateActivityCommand toCommandFromResource(Long activityId, ActivityResource resource) {
        return new UpdateActivityCommand(
                activityId, resource.questId(), resource.description(),
                resource.position(), resource.type(), resource.image()
        );
    }
}
