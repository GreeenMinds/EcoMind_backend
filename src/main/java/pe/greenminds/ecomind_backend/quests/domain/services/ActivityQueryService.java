package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivitiesByQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllActivitiesQuery;

import java.util.List;
import java.util.Optional;

public interface ActivityQueryService {
    Optional<Activity> handle(GetActivityByIdQuery query);
    List<Activity> handle(GetAllActivitiesQuery query);
    List<Activity> handle(GetActivitiesByQuestIdQuery query);
}
