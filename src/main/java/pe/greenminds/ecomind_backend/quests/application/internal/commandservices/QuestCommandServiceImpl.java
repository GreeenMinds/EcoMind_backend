package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.QuestCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Reward;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class QuestCommandServiceImpl implements QuestCommandService {

    private final QuestRepository questRepository;

    public QuestCommandServiceImpl(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Override
    public Result<Quest, ApplicationError> handle(CreateQuestCommand command) {
        try {
            var reward = new Reward(
                    command.reward_gems(),
                    command.reward_ecopoints()
            );

            var quest = new Quest(
                command.minimageId(),
                command.title(),
                command.category(),
                command.description(),
                command.type(),
                command.age(),
                command.reward_gems(),
                command.reward_ecopoints(),
                command.time(),
                command.image(),
                command.theme(),
                command.assignedDate()
            );

            return Result.success(questRepository.save(quest));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("Quest", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("Quest creation", e.getMessage())
            );
        }
    }
}
