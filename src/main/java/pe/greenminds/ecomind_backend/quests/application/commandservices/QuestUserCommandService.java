package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.shared.domain.model.ApplicationError;
import pe.greenminds.ecomind_backend.shared.domain.model.Result;

public interface QuestUserCommandService {
    Result<QuestUser, ApplicationError> handle(CreateQuestUserCommand command);
}
