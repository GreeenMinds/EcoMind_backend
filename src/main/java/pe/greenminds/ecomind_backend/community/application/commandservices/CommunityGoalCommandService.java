package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.IncrementCommunityGoalProgressCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateCommunityGoalStatusCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface CommunityGoalCommandService {
    Result<CommunityGoal, ApplicationError> handle(CreateCommunityGoalCommand command);

    Result<CommunityGoal, ApplicationError> handle(UpdateCommunityGoalCommand command);

    Result<CommunityGoal, ApplicationError> handle(IncrementCommunityGoalProgressCommand command);

    Result<CommunityGoal, ApplicationError> handle(UpdateCommunityGoalStatusCommand command);

    Result<Void, ApplicationError> handle(DeleteCommunityGoalCommand command);
}
