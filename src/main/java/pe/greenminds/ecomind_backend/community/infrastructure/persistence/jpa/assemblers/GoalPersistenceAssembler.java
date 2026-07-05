package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.GoalPersistenceEntity;

public class GoalPersistenceAssembler {
    private GoalPersistenceAssembler() {}

    public static Goal toDomainFromPersistence(GoalPersistenceEntity entity) {
        var goal = new Goal(
                entity.getTitle(),
                entity.getUnit(),
                entity.getQuestCategory()
        );
        goal.setId(entity.getId());
        return goal;
    }

    public static GoalPersistenceEntity toPersistenceFromDomain(Goal goal) {
        var entity = new GoalPersistenceEntity();
        entity.setId(goal.getId());
        entity.setTitle(goal.getTitle());
        entity.setUnit(goal.getUnit());
        entity.setQuestCategory(goal.getQuestCategory());
        return entity;
    }
}