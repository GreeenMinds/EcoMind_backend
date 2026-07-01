package pe.greenminds.ecomind_backend.quests.application.queryservices;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;

import java.util.List;

public record CollabQuestSessionState(
        CollabQuestSession session,
        QuestUser questUser,
        List<ActivityUser> activityUsers
) {
}
