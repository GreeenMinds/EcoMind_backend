package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.QuestUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;
import pe.greenminds.ecomind_backend.shared.domain.model.ApplicationError;
import pe.greenminds.ecomind_backend.shared.domain.model.Result;

@Service
public class QuestUserCommandServiceImpl implements QuestUserCommandService {
    private final QuestUserRepository questUserRepository;

    public QuestUserCommandServiceImpl(
            QuestUserRepository questUserRepository
    ) {
        this.questUserRepository = questUserRepository;
    }

    @Override
    public Result<QuestUser, ApplicationError> handle(CreateQuestUserCommand command) {
        if (questUserRepository.existsByUserIdAndQuestId(command.userId(), command.questId())) {
            return Result.failure(
                    ApplicationError.conflict(
                            "QuestUser",
                            "The quest is already assigned to this user"
                    )
            );
        }

        try {
            var questUser = new QuestUser(
                    command.userId(),
                    command.questId(),
                    command.collaborativeSessionId()
            );
            return Result.success(questUserRepository.save(questUser));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("QuestUser", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("QuestUser creation", exception.getMessage())
            );
        }
    }
}
