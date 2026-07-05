package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.GoalResource;

public class GoalResourceFromEntityAssembler {

    private GoalResourceFromEntityAssembler() {}

    public static GoalResource toResourceFromEntity(Goal goal) {
        return new GoalResource(
                goal.getId(),
                goal.getTitle(),
                goal.getUnit(),
                goal.getQuestCategory()
        );
    }
}