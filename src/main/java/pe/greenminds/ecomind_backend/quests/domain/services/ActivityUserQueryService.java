package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByActivityIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByUserIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllActivityUsersQuery;

import java.util.List;
import java.util.Optional;

public interface ActivityUserQueryService {
    Optional<ActivityUser> handle(GetActivityUserByIdQuery query);
    List<ActivityUser> handle(GetAllActivityUsersQuery query);
    List<ActivityUser> handle(GetActivityUserByUserIdQuery query);
    List<ActivityUser> handle(GetActivityUserByActivityIdQuery query);
}
