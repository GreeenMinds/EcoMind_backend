package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.QuestUserResource;

public class QuestUserResourceFromEntityAssembler {
    private QuestUserResourceFromEntityAssembler() {}

    public static QuestUserResource toResourceFromEntity(QuestUser entity) {
        return new QuestUserResource(
                entity.getId(),
                entity.getUserId(),
                entity.getQuestId(),
                entity.getStatus(),
                entity.getProgress(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getCollaborativeSessionId()
        );
    }
}
