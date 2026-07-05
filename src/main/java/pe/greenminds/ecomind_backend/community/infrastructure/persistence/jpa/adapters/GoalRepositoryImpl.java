package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.domain.repositories.GoalRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.GoalPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.GoalPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class GoalRepositoryImpl implements GoalRepository {

    private final GoalPersistenceRepository goalPersistenceRepository;

    public GoalRepositoryImpl(GoalPersistenceRepository goalPersistenceRepository) {
        this.goalPersistenceRepository = goalPersistenceRepository;
    }

    @Override
    public Optional<Goal> findById(Long id) {
        return goalPersistenceRepository.findById(id)
                .map(GoalPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Goal> findAll() {
        return goalPersistenceRepository.findAll()
                .stream()
                .map(GoalPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public Goal save(Goal goal) {
        var savedEntity = goalPersistenceRepository.save(
                GoalPersistenceAssembler.toPersistenceFromDomain(goal)
        );

        return GoalPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        goalPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return goalPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByTitle(String title) {
        return goalPersistenceRepository.existsByTitle(title);
    }

    @Override
    public boolean existsByTitleAndIdIsNot(String title, Long id) {
        return goalPersistenceRepository.existsByTitleAndIdIsNot(title, id);
    }
}