package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.Ranking;

public interface RankingCommandService {
    Long handle(CreateRankingCommand command);
}
