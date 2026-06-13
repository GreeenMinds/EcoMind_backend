package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllQuestUsersQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByUserIdQuery;

import java.util.List;
import java.util.Optional;

public interface QuestUserQueryService {
    Optional<QuestUser> handle(GetQuestUserByIdQuery query);
    List<QuestUser> handle(GetAllQuestUsersQuery query);
    List<QuestUser> handle(GetQuestUserByUserIdQuery query);
    List<QuestUser> handle(GetQuestUserByQuestIdQuery query);
}
