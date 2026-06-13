package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateQuestCommand;

import java.util.Optional;

public interface QuestCommandService {
    Long handle(CreateQuestCommand command);
    Optional<Quest> handle(UpdateQuestCommand command);
    void handle(DeleteQuestCommand command);
}
