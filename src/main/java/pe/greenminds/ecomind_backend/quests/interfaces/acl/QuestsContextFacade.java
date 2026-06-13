package pe.greenminds.ecomind_backend.quests.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateQuestCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetQuestUserByUserIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Reward;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestCommandService;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestQueryService;
import pe.greenminds.ecomind_backend.quests.domain.services.QuestUserQueryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class QuestsContextFacade {

    private final QuestCommandService questCommandService;
    private final QuestQueryService questQueryService;
    private final QuestUserQueryService questUserQueryService;

    public QuestsContextFacade(QuestCommandService questCommandService,
                                QuestQueryService questQueryService,
                                QuestUserQueryService questUserQueryService) {
        this.questCommandService = questCommandService;
        this.questQueryService = questQueryService;
        this.questUserQueryService = questUserQueryService;
    }

    public Optional<Quest> fetchQuestById(Long questId) {
        return questQueryService.handle(new GetQuestByIdQuery(questId));
    }

    public List<QuestUser> fetchQuestUsersByUserId(Long userId) {
        return questUserQueryService.handle(new GetQuestUserByUserIdQuery(userId));
    }

    public Long createQuest(String title, String description, Category category,
                            QuestType type, Integer gems, Integer ecopoints,
                            Integer age, Integer time, Theme theme) {
        var command = new CreateQuestCommand(
                null, category, title, description, null, type,
                gems, ecopoints, time, theme, age, null
        );
        return questCommandService.handle(command);
    }
}
