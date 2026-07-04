package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CommunityGoalResource;

public class CommunityGoalResourceFromEntityAssembler {

    private CommunityGoalResourceFromEntityAssembler() {}

    public static CommunityGoalResource toResourceFromEntity(CommunityGoal communityGoal) {
        return new CommunityGoalResource(
                communityGoal.getId(),
                communityGoal.getCommunityId(),
                communityGoal.getGoalId(),
                communityGoal.getDescription(),
                communityGoal.getTarget(),
                communityGoal.getProgress(),
                communityGoal.getParticipants(),
                communityGoal.getStatus().getValue()
        );
    }
}
