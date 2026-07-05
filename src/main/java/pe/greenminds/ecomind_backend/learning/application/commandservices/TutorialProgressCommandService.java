package pe.greenminds.ecomind_backend.learning.application.commandservices;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CompleteTutorialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CompleteTutorialStepCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.SkipTutorialCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface TutorialProgressCommandService {

    Result<TutorialProgress, ApplicationError> handle(CompleteTutorialStepCommand command);

    Result<TutorialProgress, ApplicationError> handle(CompleteTutorialCommand command);

    Result<TutorialProgress, ApplicationError> handle(SkipTutorialCommand command);
}
