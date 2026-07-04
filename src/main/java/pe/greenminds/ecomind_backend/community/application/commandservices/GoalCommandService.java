package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateGoalCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface GoalCommandService {
    Result<Goal, ApplicationError> handle(CreateGoalCommand command);
    Result<Goal, ApplicationError> handle(UpdateGoalCommand command);
    Result<Void, ApplicationError> handle(DeleteGoalCommand command);
}