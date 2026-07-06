package pe.greenminds.ecomind_backend.achievements.domain.repositories;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository {
    AchievementEntity save(AchievementEntity achievement);
    List<AchievementEntity> findAll();
    Optional<AchievementEntity> findById(Long id);
    long count();
}
