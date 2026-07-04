package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateNotificationCommand;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateNotificationResource;

public class CreateNotificationCommandFromResourceAssembler {
    private CreateNotificationCommandFromResourceAssembler() {}

    public static CreateNotificationCommand toCommandFromResource(CreateNotificationResource resource) {
        return new CreateNotificationCommand(resource.userId(), resource.type(), resource.title(),
                resource.message(), resource.referenceType(), resource.referenceId());
    }
}
