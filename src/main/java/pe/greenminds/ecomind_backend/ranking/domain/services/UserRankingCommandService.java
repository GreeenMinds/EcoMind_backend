package pe.greenminds.ecomind_backend.ranking.domain.services;

import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.UserRanking;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.DeleteUserRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.UpdateUserRankingCommand;

import java.util.Optional;

public interface UserRankingCommandService {
    Long handle(CreateUserRankingCommand command);
    Optional<UserRanking> handle(UpdateUserRankingCommand command);
    void handle(DeleteUserRankingCommand command);
}
