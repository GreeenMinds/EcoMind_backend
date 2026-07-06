package pe.greenminds.ecomind_backend.achievements.domain.repositories;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.FamilyAchievementEntity;

import java.util.List;

public interface FamilyAchievementRepository {
    FamilyAchievementEntity save(FamilyAchievementEntity achievement);
    boolean existsByFamilyIdAndAchievementId(Long familyId, Long achievementId);
    List<FamilyAchievementEntity> findByFamilyId(Long familyId);
}
