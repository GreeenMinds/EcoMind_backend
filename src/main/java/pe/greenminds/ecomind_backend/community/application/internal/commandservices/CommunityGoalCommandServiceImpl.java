package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.CommunityGoalCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreateCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.IncrementCommunityGoalProgressCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdateCommunityGoalStatusCommand;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityGoalRepository;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityRepository;
import pe.greenminds.ecomind_backend.community.domain.repositories.GoalRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class CommunityGoalCommandServiceImpl implements CommunityGoalCommandService {

    private final CommunityGoalRepository communityGoalRepository;
    private final CommunityRepository communityRepository;
    private final GoalRepository goalRepository;

    public CommunityGoalCommandServiceImpl(
            CommunityGoalRepository communityGoalRepository,
            CommunityRepository communityRepository,
            GoalRepository goalRepository
    ) {
        this.communityGoalRepository = communityGoalRepository;
        this.communityRepository = communityRepository;
        this.goalRepository = goalRepository;
    }

    @Override
    public Result<CommunityGoal, ApplicationError> handle(CreateCommunityGoalCommand command) {
        if (communityRepository.findById(command.communityId()).isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Community", command.communityId().toString())
            );
        }

        if (!goalRepository.existsById(command.goalId())) {
            return Result.failure(
                    ApplicationError.notFound("Goal", command.goalId().toString())
            );
        }

        if (communityGoalRepository.existsByCommunityIdAndGoalId(command.communityId(), command.goalId())) {
            return Result.failure(
                    ApplicationError.conflict("CommunityGoal", "The goal is already assigned to this community")
            );
        }

        try {
            var communityGoal = new CommunityGoal(
                    command.communityId(),
                    command.goalId(),
                    command.description(),
                    command.target(),
                    0,
                    0,
                    CommunityGoalStatus.ACTIVE
            );

            return Result.success(communityGoalRepository.save(communityGoal));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CommunityGoal", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("CommunityGoal creation", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<CommunityGoal, ApplicationError> handle(UpdateCommunityGoalCommand command) {
        var communityGoal = communityGoalRepository.findById(command.id());

        if (communityGoal.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("CommunityGoal", command.id().toString())
            );
        }

        try {
            communityGoal.get().updateInformation(
                    command.description(),
                    command.target(),
                    command.progress(),
                    command.participants(),
                    command.status()
            );

            return Result.success(communityGoalRepository.save(communityGoal.get()));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CommunityGoal", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("CommunityGoal update", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<CommunityGoal, ApplicationError> handle(IncrementCommunityGoalProgressCommand command) {
        var communityGoal = communityGoalRepository.findById(command.id());

        if (communityGoal.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("CommunityGoal", command.id().toString())
            );
        }

        try {
            communityGoal.get().incrementProgress(command.increment());
            return Result.success(communityGoalRepository.save(communityGoal.get()));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CommunityGoal progress", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("CommunityGoal progress update", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<CommunityGoal, ApplicationError> handle(UpdateCommunityGoalStatusCommand command) {
        var communityGoal = communityGoalRepository.findById(command.id());

        if (communityGoal.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("CommunityGoal", command.id().toString())
            );
        }

        try {
            communityGoal.get().updateStatus(command.status());
            return Result.success(communityGoalRepository.save(communityGoal.get()));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CommunityGoal status", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("CommunityGoal status update", exception.getMessage())
            );
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteCommunityGoalCommand command) {
        try {
            if (!communityGoalRepository.existsById(command.id())) {
                return Result.failure(
                        ApplicationError.notFound("CommunityGoal", command.id().toString())
                );
            }

            communityGoalRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("CommunityGoal deletion", exception.getMessage())
            );
        }
    }
}
