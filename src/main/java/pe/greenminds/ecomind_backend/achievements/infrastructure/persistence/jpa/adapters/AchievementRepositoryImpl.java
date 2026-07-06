package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.repositories.AchievementPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class AchievementRepositoryImpl implements AchievementRepository {

    private final AchievementPersistenceRepository achievementPersistenceRepository;

    public AchievementRepositoryImpl(AchievementPersistenceRepository achievementPersistenceRepository) {
        this.achievementPersistenceRepository = achievementPersistenceRepository;
    }

    @Override
    public AchievementEntity save(AchievementEntity achievement) {
        return achievementPersistenceRepository.save(achievement);
    }

    @Override
    public List<AchievementEntity> findAll() {
        return achievementPersistenceRepository.findAll();
    }

    @Override
    public Optional<AchievementEntity> findById(Long id) {
        return achievementPersistenceRepository.findById(id);
    }

    @Override
    public long count() {
        return achievementPersistenceRepository.count();
    }
}
