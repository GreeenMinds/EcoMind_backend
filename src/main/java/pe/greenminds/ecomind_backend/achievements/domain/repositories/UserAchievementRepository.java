package pe.greenminds.ecomind_backend.achievements.domain.repositories;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.UserAchievementEntity;

import java.util.List;

public interface UserAchievementRepository {
    UserAchievementEntity save(UserAchievementEntity achievement);
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);
    List<UserAchievementEntity> findByUserId(Long userId);
}
