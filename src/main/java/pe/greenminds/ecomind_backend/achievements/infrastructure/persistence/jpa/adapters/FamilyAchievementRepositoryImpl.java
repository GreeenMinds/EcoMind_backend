package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.FamilyAchievementRepository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.FamilyAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories.FamilyAchievementPersistenceRepository;

import java.util.List;

@Repository
public class FamilyAchievementRepositoryImpl implements FamilyAchievementRepository {

    private final FamilyAchievementPersistenceRepository familyAchievementPersistenceRepository;

    public FamilyAchievementRepositoryImpl(FamilyAchievementPersistenceRepository familyAchievementPersistenceRepository) {
        this.familyAchievementPersistenceRepository = familyAchievementPersistenceRepository;
    }

    @Override
    public FamilyAchievementEntity save(FamilyAchievementEntity achievement) {
        return familyAchievementPersistenceRepository.save(achievement);
    }

    @Override
    public boolean existsByFamilyIdAndAchievementId(Long familyId, Long achievementId) {
        return familyAchievementPersistenceRepository.existsByFamilyIdAndAchievementId(familyId, achievementId);
    }

    @Override
    public List<FamilyAchievementEntity> findByFamilyId(Long familyId) {
        return familyAchievementPersistenceRepository.findByFamilyId(familyId);
    }
}
