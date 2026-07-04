package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;

import java.util.List;
import java.util.Optional;

public interface CommunityGoalRepository {
    Optional<CommunityGoal> findById(Long id);

    List<CommunityGoal> search(Long communityId);

    CommunityGoal save(CommunityGoal communityGoal);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByCommunityIdAndGoalId(Long communityId, Long goalId);
}
