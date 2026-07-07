package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.TutorialProgressEntity;

public class TutorialProgressPersistenceAssembler {
    private TutorialProgressPersistenceAssembler() {}

    public static TutorialProgress toDomainFromPersistence(TutorialProgressEntity entity) {
        var progress = new TutorialProgress(
                entity.getUserId(),
                entity.getCurrentStep(),
                entity.getTotalSteps(),
                entity.isCompleted(),
                entity.isSkipped(),
                entity.getCompletedAt()
        );
        progress.setId(entity.getId());
        return progress;
    }

    public static TutorialProgressEntity toPersistenceFromDomain(TutorialProgress progress) {
        var entity = new TutorialProgressEntity();
        entity.setId(progress.getId());
        entity.setUserId(progress.getUserId());
        entity.setCurrentStep(progress.getCurrentStep());
        entity.setTotalSteps(progress.getTotalSteps());
        entity.setCompleted(progress.isCompleted());
        entity.setSkipped(progress.isSkipped());
        entity.setCompletedAt(progress.getCompletedAt());
        return entity;
    }
}
