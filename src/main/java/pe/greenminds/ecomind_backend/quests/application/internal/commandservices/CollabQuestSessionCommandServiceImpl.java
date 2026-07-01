package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.CollabQuestSessionCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateCollabQuestSessionCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestSessionRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class CollabQuestSessionCommandServiceImpl implements CollabQuestSessionCommandService {
    private final CollabQuestSessionRepository collabQuestSessionRepository;
    private final QuestRepository questRepository;

    public CollabQuestSessionCommandServiceImpl(
            CollabQuestSessionRepository collabQuestSessionRepository,
            QuestRepository questRepository
    ) {
        this.collabQuestSessionRepository = collabQuestSessionRepository;
        this.questRepository = questRepository;
    }

    @Override
    public Result<CollabQuestSession, ApplicationError> handle(
            CreateCollabQuestSessionCommand command
    ) {
        var quest = questRepository.findById(command.questId());
        if (quest.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("Quest", command.questId().toString())
            );
        }

        if (quest.get().getType() != QuestType.COLLABORATIVE) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only collaborative quests can have collaborative sessions",
                            "Quest %d is not collaborative".formatted(command.questId())
                    )
            );
        }

        if (collabQuestSessionRepository
                .findByQuestIdAndOwnerUserId(command.questId(), command.ownerUserId())
                .isPresent()) {
            return Result.failure(
                    ApplicationError.conflict(
                            "CollabQuestSession",
                            "The user already has a collaborative session for this quest"
                    )
            );
        }

        try {
            var collabQuestSession = new CollabQuestSession(
                    command.questId(),
                    command.ownerUserId()
            );

            return Result.success(collabQuestSessionRepository.save(collabQuestSession));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError(
                            "CollabQuestSession",
                            exception.getMessage()
                    )
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected(
                            "CollabQuestSession creation",
                            exception.getMessage()
                    )
            );
        }
    }
}
