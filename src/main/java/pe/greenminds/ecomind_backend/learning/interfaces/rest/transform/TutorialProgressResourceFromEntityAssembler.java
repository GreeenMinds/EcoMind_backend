package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.TutorialProgressResource;

public class TutorialProgressResourceFromEntityAssembler {

    private TutorialProgressResourceFromEntityAssembler() {}

    public static TutorialProgressResource toResourceFromEntity(TutorialProgress entity) {
        return new TutorialProgressResource(
                entity.getId(),
                entity.getUserId(),
                entity.getCurrentStep(),
                entity.getTotalSteps(),
                entity.isCompleted(),
                entity.isSkipped(),
                entity.getCompletedAt() != null ? entity.getCompletedAt().toString() : null
        );
    }
}
