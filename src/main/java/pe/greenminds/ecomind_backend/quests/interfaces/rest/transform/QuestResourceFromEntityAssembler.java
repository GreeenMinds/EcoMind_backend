package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.QuestResource;

public class QuestResourceFromEntityAssembler {

    private QuestResourceFromEntityAssembler() {}

    public static QuestResource toResourceFromEntity(Quest quest) {
        return new QuestResource(
                quest.getId(),
                quest.getMinigameId(),
                quest.getTitle(),
                quest.getDescription(),
                quest.getCategory(),
                quest.getType(),
                quest.getGems(),
                quest.getEcopoints(),
                quest.getAge(),
                quest.getTime(),
                quest.getTheme(),
                quest.getAssignedDate(),
                quest.getExpirationDate(),
                quest.getImage()
        );
    }
}
