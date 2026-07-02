package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.MinigameCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Minigame;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateMinigameCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.MinigameRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.util.Map;

@Service
public class MinigameCommandServiceImpl implements MinigameCommandService {
    private static final String MIN_SCORE_RULE = "minScore";

    private final MinigameRepository minigameRepository;

    public MinigameCommandServiceImpl(MinigameRepository minigameRepository) {
        this.minigameRepository = minigameRepository;
    }

    @Override
    public Result<Minigame, ApplicationError> handle(CreateMinigameCommand command) {
        try {
            validateCompletionRules(command.completionRules());
            return Result.success(
                    minigameRepository.save(
                            new Minigame(
                                    null,
                                    command.name(),
                                    command.description(),
                                    command.url(),
                                    command.completionRules()
                            )
                    )
            );
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("Minigame", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("Minigame creation", exception.getMessage())
            );
        }
    }

    private void validateCompletionRules(Map<String, Object> completionRules) {
        if (completionRules == null || !(completionRules.get(MIN_SCORE_RULE) instanceof Number)) {
            throw new IllegalArgumentException("completionRules.minScore must be numeric");
        }
    }
}
