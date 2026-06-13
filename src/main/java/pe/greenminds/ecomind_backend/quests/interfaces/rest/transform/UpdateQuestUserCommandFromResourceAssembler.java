package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.QuestUserResource;

public class UpdateQuestUserCommandFromResourceAssembler {
    private UpdateQuestUserCommandFromResourceAssembler() {}

    public static UpdateQuestUserCommand toCommandFromResource(Long id, QuestUserResource resource) {
        return new UpdateQuestUserCommand(
                id, resource.userId(), resource.questId(),
                resource.status(), resource.progress(), resource.endDate(),
                resource.collaborativeSessionId()
        );
    }
}
