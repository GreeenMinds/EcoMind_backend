package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.GoalCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.repositories.GoalRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class GoalCommandServiceImpl implements GoalCommandService {

    private final GoalRepository goalRepository;

    public GoalCommandServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public Result<Goal, ApplicationError> handle(CreateGoalCommand command) {
        if (goalRepository.existsByTitle(command.title())) {
            return Result.failure(
                    ApplicationError.conflict("Goal", "The title already exists")
            );
        }
        try {
            var goal = new Goal(
                    command.title(),
                    command.unit(),
                    command.questCategory()
            );
            return Result.success(goalRepository.save(goal));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("Goal", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("Goal creation", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<Goal, ApplicationError> handle(UpdateGoalCommand command) {
        var goal = goalRepository.findById(command.id());

        if (goal.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Goal", command.id().toString())
            );
        }
        if (goalRepository.existsByTitleAndIdIsNot(command.title(), command.id())) {
            return Result.failure(
                    ApplicationError.conflict("Goal", "The title already exists")
            );
        }
        try {
            goal.get().updateInformation(
                    command.title(),
                    command.unit(),
                    command.questCategory()
            );
            return Result.success(goalRepository.save(goal.get()));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("Goal", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("Goal update", exception.getMessage())
            );
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteGoalCommand command) {
        try {
            if (!goalRepository.existsById(command.id())) {
                return Result.failure(
                        ApplicationError.notFound("Goal", command.id().toString())
                );
            }
            goalRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("Goal deletion", exception.getMessage())
            );
        }
    }
}