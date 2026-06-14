package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.queryservices.QuestUserQueryService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByUserIdAndQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUsersByUserIdAndStatusQuery;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestUserQueryServiceImpl implements QuestUserQueryService {
    private final QuestUserRepository questUserRepository;

    public QuestUserQueryServiceImpl(QuestUserRepository questUserRepository) {
        this.questUserRepository = questUserRepository;
    }

    @Override
    public Optional<QuestUser> handle(GetQuestUserByIdQuery query) {
        return questUserRepository.findById(query.id());
    }

    @Override
    public Optional<QuestUser> handle(GetQuestUserByUserIdAndQuestIdQuery query) {
        return questUserRepository.findByUserIdAndQuestId(query.userId(), query.questId());
    }

    @Override
    public List<QuestUser> handle(GetQuestUsersByUserIdAndStatusQuery query) {
        return questUserRepository.findByUserIdAndStatus(query.userId(), query.status());
    }
}
