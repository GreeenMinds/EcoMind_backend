package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.CommunityAchievementEntity;

import java.util.List;

@Repository
public interface CommunityAchievementPersistenceRepository extends JpaRepository<CommunityAchievementEntity, Long> {
    boolean existsByCommunityIdAndAchievementId(Long communityId, Long achievementId);
    List<CommunityAchievementEntity> findByCommunityId(Long communityId);
}
