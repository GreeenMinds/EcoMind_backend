package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.CommunityGoalQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetCommunityGoalByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchCommunityGoalsQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.CommunityGoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityGoalQueryServiceImpl implements CommunityGoalQueryService {

    private final CommunityGoalRepository communityGoalRepository;

    public CommunityGoalQueryServiceImpl(CommunityGoalRepository communityGoalRepository) {
        this.communityGoalRepository = communityGoalRepository;
    }

    @Override
    public List<CommunityGoal> handle(SearchCommunityGoalsQuery query) {
        return communityGoalRepository.search(query.communityId());
    }

    @Override
    public Optional<CommunityGoal> handle(GetCommunityGoalByIdQuery query) {
        return communityGoalRepository.findById(query.id());
    }
}
