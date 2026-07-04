package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.GoalQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetAllGoalsQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetGoalByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.GoalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GoalQueryServiceImpl implements GoalQueryService {

    private final GoalRepository goalRepository;

    public GoalQueryServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public List<Goal> handle(GetAllGoalsQuery query) {
        return goalRepository.findAll();
    }

    @Override
    public Optional<Goal> handle(GetGoalByIdQuery query) {
        return goalRepository.findById(query.id());
    }
}