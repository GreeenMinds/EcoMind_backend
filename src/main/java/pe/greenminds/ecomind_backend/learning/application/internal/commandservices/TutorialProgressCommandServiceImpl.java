package pe.greenminds.ecomind_backend.learning.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.learning.application.commandservices.TutorialProgressCommandService;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CompleteTutorialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CompleteTutorialStepCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.SkipTutorialCommand;
import pe.greenminds.ecomind_backend.learning.domain.repositories.TutorialProgressRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class TutorialProgressCommandServiceImpl implements TutorialProgressCommandService {

    private static final int DEFAULT_TOTAL_STEPS = 5;

    private final TutorialProgressRepository tutorialProgressRepository;

    public TutorialProgressCommandServiceImpl(TutorialProgressRepository tutorialProgressRepository) {
        this.tutorialProgressRepository = tutorialProgressRepository;
    }

    @Override
    public Result<TutorialProgress, ApplicationError> handle(CompleteTutorialStepCommand command) {
        try {
            var existing = tutorialProgressRepository.findByUserId(command.userId());
            TutorialProgress progress;
            if (existing.isEmpty()) {
                progress = new TutorialProgress(
                        command.userId(),
                        1,
                        DEFAULT_TOTAL_STEPS,
                        false,
                        false,
                        null
                );
            } else {
                progress = existing.get();
                progress.advanceStep();
            }
            return Result.success(tutorialProgressRepository.save(progress));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("TutorialProgress", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("TutorialProgress step completion", e.getMessage())
            );
        }
    }

    @Override
    public Result<TutorialProgress, ApplicationError> handle(CompleteTutorialCommand command) {
        try {
            var existing = tutorialProgressRepository.findByUserId(command.userId());
            if (existing.isEmpty()) {
                return Result.failure(
                        ApplicationError.notFound("TutorialProgress", command.userId().toString())
                );
            }
            var progress = existing.get();
            progress.complete();
            return Result.success(tutorialProgressRepository.save(progress));
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("TutorialProgress completion", e.getMessage())
            );
        }
    }

    @Override
    public Result<TutorialProgress, ApplicationError> handle(SkipTutorialCommand command) {
        try {
            var existing = tutorialProgressRepository.findByUserId(command.userId());
            TutorialProgress progress;
            if (existing.isEmpty()) {
                progress = new TutorialProgress(command.userId(), DEFAULT_TOTAL_STEPS, DEFAULT_TOTAL_STEPS, false, false, null);
                progress.skip();
            } else {
                progress = existing.get();
                progress.skip();
            }
            return Result.success(tutorialProgressRepository.save(progress));
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("TutorialProgress skip", e.getMessage())
            );
        }
    }
}
