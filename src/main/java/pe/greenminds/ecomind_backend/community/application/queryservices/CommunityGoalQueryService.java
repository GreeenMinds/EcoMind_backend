package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.CommunityGoal;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetCommunityGoalByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchCommunityGoalsQuery;

import java.util.List;
import java.util.Optional;

public interface CommunityGoalQueryService {
    List<CommunityGoal> handle(SearchCommunityGoalsQuery query);

    Optional<CommunityGoal> handle(GetCommunityGoalByIdQuery query);
}
