package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.CommunityGoalPersistenceEntity;

public class CommunityGoalPersistenceAssembler {
    private CommunityGoalPersistenceAssembler() {}

    public static CommunityGoal toDomainFromPersistence(CommunityGoalPersistenceEntity entity) {
        var communityGoal = new CommunityGoal(
                entity.getCommunityId(),
                entity.getGoalId(),
                entity.getDescription(),
                entity.getTarget(),
                entity.getProgress(),
                entity.getParticipants(),
                entity.getStatus()
        );

        communityGoal.setId(entity.getId());
        return communityGoal;
    }

    public static CommunityGoalPersistenceEntity toPersistenceFromDomain(CommunityGoal communityGoal) {
        var entity = new CommunityGoalPersistenceEntity();

        entity.setId(communityGoal.getId());
        entity.setCommunityId(communityGoal.getCommunityId());
        entity.setGoalId(communityGoal.getGoalId());
        entity.setDescription(communityGoal.getDescription());
        entity.setTarget(communityGoal.getTarget());
        entity.setProgress(communityGoal.getProgress());
        entity.setParticipants(communityGoal.getParticipants());
        entity.setStatus(communityGoal.getStatus());

        return entity;
    }
}
