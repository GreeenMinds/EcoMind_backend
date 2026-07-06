package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.CommunityAchievementRepository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.CommunityAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories.CommunityAchievementPersistenceRepository;

import java.util.List;

@Repository
public class CommunityAchievementRepositoryImpl implements CommunityAchievementRepository {

    private final CommunityAchievementPersistenceRepository communityAchievementPersistenceRepository;

    public CommunityAchievementRepositoryImpl(CommunityAchievementPersistenceRepository communityAchievementPersistenceRepository) {
        this.communityAchievementPersistenceRepository = communityAchievementPersistenceRepository;
    }

    @Override
    public CommunityAchievementEntity save(CommunityAchievementEntity achievement) {
        return communityAchievementPersistenceRepository.save(achievement);
    }

    @Override
    public boolean existsByCommunityIdAndAchievementId(Long communityId, Long achievementId) {
        return communityAchievementPersistenceRepository.existsByCommunityIdAndAchievementId(communityId, achievementId);
    }

    @Override
    public List<CommunityAchievementEntity> findByCommunityId(Long communityId) {
        return communityAchievementPersistenceRepository.findByCommunityId(communityId);
    }
}
