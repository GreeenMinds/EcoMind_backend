package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllQuestUsersQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByUserIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestUserQueryService;
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
    public List<QuestUser> handle(GetAllQuestUsersQuery query) {
        return questUserRepository.findAll();
    }

    @Override
    public List<QuestUser> handle(GetQuestUserByUserIdQuery query) {
        return questUserRepository.findByUserId(query.userId());
    }

    @Override
    public List<QuestUser> handle(GetQuestUserByQuestIdQuery query) {
        return questUserRepository.findByQuestId(query.questId());
    }
}
