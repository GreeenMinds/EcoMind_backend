package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestUserCommand;

import java.util.Optional;

public interface QuestUserCommandService {
    Long handle(CreateQuestUserCommand command);
    Optional<QuestUser> handle(UpdateQuestUserCommand command);
    void handle(DeleteQuestUserCommand command);
}
