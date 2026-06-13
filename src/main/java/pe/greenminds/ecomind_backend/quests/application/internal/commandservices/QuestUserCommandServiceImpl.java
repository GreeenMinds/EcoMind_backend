package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;

import java.util.Optional;

@Service
public class QuestUserCommandServiceImpl implements QuestUserCommandService {

    private final QuestUserRepository questUserRepository;
    private final QuestRepository questRepository;

    public QuestUserCommandServiceImpl(QuestUserRepository questUserRepository, QuestRepository questRepository) {
        this.questUserRepository = questUserRepository;
        this.questRepository = questRepository;
    }

    @Override
    public Long handle(CreateQuestUserCommand command) {
        if (!questRepository.existsById(command.questId()))
            throw new IllegalArgumentException("Quest with id " + command.questId() + " not found");
        if (questUserRepository.findByUserIdAndQuestId(command.userId(), command.questId()).isPresent())
            throw new IllegalArgumentException("Quest user already exists for this user and quest");

        var questUser = new QuestUser(command.userId(), command.questId());
        return questUserRepository.save(questUser).getId();
    }

    @Override
    public Optional<QuestUser> handle(UpdateQuestUserCommand command) {
        var result = questUserRepository.findById(command.id());
        if (result.isEmpty()) return Optional.empty();
        var questUser = result.get();
        questUser.updateInformation(
                command.status(), command.progress(),
                command.endDate(), command.collaborativeSessionId()
        );
        return Optional.of(questUserRepository.save(questUser));
    }

    @Override
    public void handle(DeleteQuestUserCommand command) {
        if (!questUserRepository.existsById(command.id()))
            throw new IllegalArgumentException("QuestUser with id " + command.id() + " not found");
        questUserRepository.deleteById(command.id());
    }
}
