package pe.greenminds.ecomind_backend.quests.application.queryservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Minigame;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllMinigamesQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetMinigameByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MinigameQueryService {
    List<Minigame> handle(GetAllMinigamesQuery query);
    Optional<Minigame> handle(GetMinigameByIdQuery query);
}
