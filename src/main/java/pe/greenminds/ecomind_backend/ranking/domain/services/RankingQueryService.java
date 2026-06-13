package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.Ranking;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetRankingByIdQuery;

import java.util.List;
import java.util.Optional;

public interface RankingQueryService {
    List<Ranking> handle(GetAllRankingsQuery query);
    Optional<Ranking> handle(GetRankingByIdQuery query);
}
