package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.UserRanking;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllUserRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetUserRankingsByRankingIdQuery;

import java.util.List;
import java.util.Optional;

public interface UserRankingQueryService {
    List<UserRanking> handle(GetAllUserRankingsQuery query);
    Optional<UserRanking> handle(GetUserRankingByIdQuery query);
    List<UserRanking> handle(GetUserRankingsByRankingIdQuery query);
}
