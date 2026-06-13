package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateQuestResource;

public class CreateQuestCommandFromResourceAssembler {

    private CreateQuestCommandFromResourceAssembler() {}

    public static CreateQuestCommand toCommandFromResource(
            CreateQuestResource resource
    ) {
        return new CreateQuestCommand(
                resource.minigameId(),
                resource.category(),
                resource.title(),
                resource.description(),
                resource.image(),
                resource.type(),
                resource.gemReward(),
                resource.ecopoints(),
                resource.time(),
                resource.theme(),
                resource.age(),
                resource.assignedDate()

        );
    }
}
