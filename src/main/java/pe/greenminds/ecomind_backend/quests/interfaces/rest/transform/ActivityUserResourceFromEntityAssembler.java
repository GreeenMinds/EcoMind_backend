package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityUserResource;

public class ActivityUserResourceFromEntityAssembler {
    private ActivityUserResourceFromEntityAssembler() {}

    public static ActivityUserResource toResourceFromEntity(ActivityUser entity) {
        return new ActivityUserResource(
                entity.getId(),
                entity.getUserId(),
                entity.getActivityId(),
                entity.getProgress(),
                entity.getEndDate(),
                entity.getCollaborativeSessionId()
        );
    }
}
