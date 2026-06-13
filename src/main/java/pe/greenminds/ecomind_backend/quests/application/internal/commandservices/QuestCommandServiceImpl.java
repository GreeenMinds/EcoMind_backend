package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Reward;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestCommandService;

import java.util.Optional;

@Service
public class QuestCommandServiceImpl implements QuestCommandService {

    private final QuestRepository questRepository;

    public QuestCommandServiceImpl(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Override
    public Long handle(CreateQuestCommand command) {
        var reward = new Reward(command.rewardGems(), command.rewardEcopoints());
        var quest = new Quest(
            command.minigameId(), command.title(), command.category(),
            command.description(), command.type(), command.age(), reward,
            command.time(), command.image(), command.theme(),
            command.assignedDate(), null
        );
        return questRepository.save(quest).getId();
    }

    @Override
    public Optional<Quest> handle(UpdateQuestCommand command) {
        var result = questRepository.findById(command.questId());
        if (result.isEmpty()) return Optional.empty();
        var quest = result.get();
        quest.updateInformation(
            command.title(), command.description(), command.category(),
            command.type(), command.age(), new Reward(command.rewardGems(), command.rewardEcopoints()),
            command.time(), command.image(), command.theme(),
            command.assignedDate(), command.expirationDate()
        );
        return Optional.of(questRepository.save(quest));
    }

    @Override
    public void handle(DeleteQuestCommand command) {
        if (!questRepository.existsById(command.questId()))
            throw new IllegalArgumentException("Quest with id " + command.questId() + " not found");
        questRepository.deleteById(command.questId());
    }
}
