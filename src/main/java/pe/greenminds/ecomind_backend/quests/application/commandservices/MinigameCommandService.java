package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Minigame;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateMinigameCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface MinigameCommandService {
    Result<Minigame, ApplicationError> handle(CreateMinigameCommand command);
}
