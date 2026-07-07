package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.queryservices.MinigameAttemptQueryService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.MinigameAttempt;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetMinigameAttemptsByUserAndMinigameQuery;
import pe.greenminds.ecomind_backend.quests.domain.repositories.MinigameAttemptRepository;

import java.util.List;

@Service
public class MinigameAttemptQueryServiceImpl implements MinigameAttemptQueryService {
    private final MinigameAttemptRepository minigameAttemptRepository;

    public MinigameAttemptQueryServiceImpl(MinigameAttemptRepository minigameAttemptRepository) {
        this.minigameAttemptRepository = minigameAttemptRepository;
    }

    @Override
    public List<MinigameAttempt> handle(GetMinigameAttemptsByUserAndMinigameQuery query) {
        return minigameAttemptRepository.findByUserIdAndMinigameId(
                query.userId(),
                query.minigameId()
        );
    }
}
