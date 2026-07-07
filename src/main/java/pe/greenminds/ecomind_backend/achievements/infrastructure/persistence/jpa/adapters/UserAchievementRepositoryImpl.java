package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.UserAchievementRepository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.UserAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories.UserAchievementPersistenceRepository;

import java.util.List;

@Repository
public class UserAchievementRepositoryImpl implements UserAchievementRepository {

    private final UserAchievementPersistenceRepository userAchievementPersistenceRepository;

    public UserAchievementRepositoryImpl(UserAchievementPersistenceRepository userAchievementPersistenceRepository) {
        this.userAchievementPersistenceRepository = userAchievementPersistenceRepository;
    }

    @Override
    public UserAchievementEntity save(UserAchievementEntity achievement) {
        return userAchievementPersistenceRepository.save(achievement);
    }

    @Override
    public boolean existsByUserIdAndAchievementId(Long userId, Long achievementId) {
        return userAchievementPersistenceRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }

    @Override
    public List<UserAchievementEntity> findByUserId(Long userId) {
        return userAchievementPersistenceRepository.findByUserId(userId);
    }
}
