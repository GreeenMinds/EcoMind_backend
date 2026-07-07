package pe.greenminds.ecomind_backend.achievements.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.achievements.domain.model.commands.CreateAchievementCommand;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources.CreateAchievementResource;

public class CreateAchievementCommandFromResourceAssembler {
    public static CreateAchievementCommand toCommandFromResource(CreateAchievementResource resource) {
        return new CreateAchievementCommand(
                resource.name(),
                resource.description(),
                resource.type(),
                resource.thresholdValue()
        );
    }
}
