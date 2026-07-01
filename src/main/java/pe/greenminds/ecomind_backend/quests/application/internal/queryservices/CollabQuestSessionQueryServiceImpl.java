package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionQueryService;
import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionState;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetCollabQuestSessionStateQuery;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestSessionRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;

import java.util.List;

@Service
public class CollabQuestSessionQueryServiceImpl implements CollabQuestSessionQueryService {
    private final CollabQuestSessionRepository collabQuestSessionRepository;
    private final QuestUserRepository questUserRepository;
    private final ActivityUserRepository activityUserRepository;

    public CollabQuestSessionQueryServiceImpl(
            CollabQuestSessionRepository collabQuestSessionRepository,
            QuestUserRepository questUserRepository,
            ActivityUserRepository activityUserRepository
    ) {
        this.collabQuestSessionRepository = collabQuestSessionRepository;
        this.questUserRepository = questUserRepository;
        this.activityUserRepository = activityUserRepository;
    }

    @Override
    public CollabQuestSessionState handle(GetCollabQuestSessionStateQuery query) {
        var questUser = questUserRepository
                .findByUserIdAndQuestId(query.userId(), query.questId())
                .orElse(null);
        var session = questUser != null && questUser.getCollaborativeSessionId() != null
                ? collabQuestSessionRepository
                        .findById(questUser.getCollaborativeSessionId())
                        .orElse(null)
                : collabQuestSessionRepository
                        .findByQuestIdAndOwnerUserId(query.questId(), query.userId())
                        .orElse(null);
        var activityUsers = questUser == null
                ? List.<ActivityUser>of()
                : activityUserRepository.findByQuestUserId(questUser.getId());

        return new CollabQuestSessionState(session, questUser, activityUsers);
    }
}
