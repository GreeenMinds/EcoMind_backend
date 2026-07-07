package pe.greenminds.ecomind_backend.achievements.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.repositories.FamilyPlanRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.MinigameAttemptRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestUserRepository;

import java.util.List;

@Service
public class QuestAchievementExternalService {
    private static final List<QuestType> QUEST_USER_ACHIEVEMENT_TYPES = List.of(
            QuestType.DAILY_QUEST,
            QuestType.ACTIVITIES,
            QuestType.COLLABORATIVE
    );

    private final QuestUserRepository questUserRepository;
    private final MinigameAttemptRepository minigameAttemptRepository;
    private final FamilyPlanRepository familyPlanRepository;

    public QuestAchievementExternalService(
            QuestUserRepository questUserRepository,
            MinigameAttemptRepository minigameAttemptRepository,
            FamilyPlanRepository familyPlanRepository
    ) {
        this.questUserRepository = questUserRepository;
        this.minigameAttemptRepository = minigameAttemptRepository;
        this.familyPlanRepository = familyPlanRepository;
    }

    public int countCompletedAchievementQuestsByUserIds(List<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return 0;
        }

        var completedQuestUsers = questUserRepository.countByUserIdsAndStatusAndQuestTypes(
                userIds,
                QuestStatus.COMPLETED,
                QUEST_USER_ACHIEVEMENT_TYPES
        );
        var completedMinigames = minigameAttemptRepository.countRewardedCompletedByUserIds(
                userIds
        );

        return completedQuestUsers + completedMinigames;
    }

    public int countCompletedFamilyPlans(Long familyId) {
        return (int) familyPlanRepository.findByFamilyId(familyId).stream()
                .filter(plan -> plan.getStatus() == FamilyPlanStatus.COMPLETED)
                .count();
    }
}
