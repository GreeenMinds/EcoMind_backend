package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionQueryService;
import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionState;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestMember;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetCollabQuestSessionStateQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabMemberStatus;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestSessionRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestMemberRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;

import java.util.List;

@Service
public class CollabQuestSessionQueryServiceImpl implements CollabQuestSessionQueryService {
    private final CollabQuestSessionRepository collabQuestSessionRepository;
    private final CollabQuestMemberRepository collabQuestMemberRepository;
    private final QuestUserRepository questUserRepository;

    public CollabQuestSessionQueryServiceImpl(
            CollabQuestSessionRepository collabQuestSessionRepository,
            CollabQuestMemberRepository collabQuestMemberRepository,
            QuestUserRepository questUserRepository
    ) {
        this.collabQuestSessionRepository = collabQuestSessionRepository;
        this.collabQuestMemberRepository = collabQuestMemberRepository;
        this.questUserRepository = questUserRepository;
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
        var members = session == null
                ? List.<CollabQuestMember>of()
                : collabQuestMemberRepository.findBySessionIdAndStatusIn(
                        session.getId(),
                        List.of(CollabMemberStatus.PENDING, CollabMemberStatus.ACCEPTED)
                );

        return new CollabQuestSessionState(session, members);
    }
}
