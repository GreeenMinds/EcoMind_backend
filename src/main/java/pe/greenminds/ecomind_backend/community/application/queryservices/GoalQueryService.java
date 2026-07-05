package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetAllGoalsQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetGoalByIdQuery;

import java.util.List;
import java.util.Optional;

public interface GoalQueryService {
    List<Goal> handle(GetAllGoalsQuery query);
    Optional<Goal> handle(GetGoalByIdQuery query);
}