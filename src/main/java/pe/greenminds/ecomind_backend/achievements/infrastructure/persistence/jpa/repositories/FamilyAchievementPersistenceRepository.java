package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.FamilyAchievementEntity;

import java.util.List;

@Repository
public interface FamilyAchievementPersistenceRepository extends JpaRepository<FamilyAchievementEntity, Long> {
    boolean existsByFamilyIdAndAchievementId(Long familyId, Long achievementId);
    List<FamilyAchievementEntity> findByFamilyId(Long familyId);
}
