package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.UserAchievementEntity;

import java.util.List;

@Repository
public interface UserAchievementPersistenceRepository extends JpaRepository<UserAchievementEntity, Long> {
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);
    List<UserAchievementEntity> findByUserId(Long userId);
}
