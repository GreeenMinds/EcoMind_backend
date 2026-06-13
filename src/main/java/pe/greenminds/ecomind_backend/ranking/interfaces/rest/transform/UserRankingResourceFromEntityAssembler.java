package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.UserRanking;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.UserRankingResource;

public class UserRankingResourceFromEntityAssembler {
    public static UserRankingResource toResourceFromEntity(UserRanking entity) {
        return new UserRankingResource(
                entity.getId(),
                entity.getUserId(),
                entity.getRankingId(),
                entity.getRankPosition(),
                entity.getScore()
        );
    }
}
