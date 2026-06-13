package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllQuestsQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.SearchQuestQuery;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestQueryService;

import java.util.List;
import java.util.Optional;

@Service
public class QuestQueryServiceImpl implements QuestQueryService {

    private final QuestRepository questRepository;

    public QuestQueryServiceImpl(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Override
    public Optional<Quest> handle(GetQuestByIdQuery query) {
        return questRepository.findById(query.id());
    }

    @Override
    public List<Quest> handle(GetAllQuestsQuery query) {
        return questRepository.findAll();
    }

    @Override
    public List<Quest> handle(SearchQuestQuery query) {
        return questRepository.search(
                query.title(),
                query.category(),
                query.questType(),
                query.age(),
                query.type()
        );
    }
}
