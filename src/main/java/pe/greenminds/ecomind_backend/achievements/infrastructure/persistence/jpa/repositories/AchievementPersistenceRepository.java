package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;

@Repository
public interface AchievementPersistenceRepository extends JpaRepository<AchievementEntity, Long> {
}
