package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateQuestUserResource;

public class CreateQuestUserCommandFromResourceAssembler {
    private CreateQuestUserCommandFromResourceAssembler() {}

    public static CreateQuestUserCommand toCommandFromResource(CreateQuestUserResource resource) {
        return new CreateQuestUserCommand(resource.userId(), resource.questId());
    }
}
