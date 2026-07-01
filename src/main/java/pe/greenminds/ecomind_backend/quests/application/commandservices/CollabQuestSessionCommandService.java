package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateCollabQuestSessionCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface CollabQuestSessionCommandService {
    Result<CollabQuestSession, ApplicationError> handle(CreateCollabQuestSessionCommand command);
}
