package pe.greenminds.ecomind_backend.quests.application.commandservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.MinigameAttempt;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CancelMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.FinishMinigameAttemptCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface MinigameAttemptCommandService {
    Result<MinigameAttempt, ApplicationError> handle(CreateMinigameAttemptCommand command);
    Result<MinigameAttempt, ApplicationError> handle(FinishMinigameAttemptCommand command);
    Result<MinigameAttempt, ApplicationError> handle(CancelMinigameAttemptCommand command);
}
