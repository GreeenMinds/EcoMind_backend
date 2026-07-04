package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityGoalRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.CommunityGoalPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.CommunityGoalPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommunityGoalRepositoryImpl implements CommunityGoalRepository {

    private final CommunityGoalPersistenceRepository communityGoalPersistenceRepository;

    public CommunityGoalRepositoryImpl(CommunityGoalPersistenceRepository communityGoalPersistenceRepository) {
        this.communityGoalPersistenceRepository = communityGoalPersistenceRepository;
    }

    @Override
    public Optional<CommunityGoal> findById(Long id) {
        return communityGoalPersistenceRepository.findById(id)
                .map(CommunityGoalPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<CommunityGoal> search(Long communityId) {
        return communityGoalPersistenceRepository.search(communityId)
                .stream()
                .map(CommunityGoalPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public CommunityGoal save(CommunityGoal communityGoal) {
        var savedEntity = communityGoalPersistenceRepository.save(
                CommunityGoalPersistenceAssembler.toPersistenceFromDomain(communityGoal)
        );

        return CommunityGoalPersistenceAssembler.toDomainFromPersistence(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        communityGoalPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return communityGoalPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByCommunityIdAndGoalId(Long communityId, Long goalId) {
        return communityGoalPersistenceRepository.existsByCommunityIdAndGoalId(communityId, goalId);
    }
}
