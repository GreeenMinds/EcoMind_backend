package pe.greenminds.ecomind_backend.achievements.domain.repositories;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.CommunityAchievementEntity;

import java.util.List;

public interface CommunityAchievementRepository {
    CommunityAchievementEntity save(CommunityAchievementEntity achievement);
    boolean existsByCommunityIdAndAchievementId(Long communityId, Long achievementId);
    List<CommunityAchievementEntity> findByCommunityId(Long communityId);
}
