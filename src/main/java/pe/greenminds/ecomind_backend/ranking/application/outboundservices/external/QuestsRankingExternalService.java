package pe.greenminds.ecomind_backend.ranking.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.FamilyPlanItemRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.FamilyPlanRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.CompletedQuestSummary;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class QuestsRankingExternalService {

    private final FamilyPlanRepository familyPlanRepository;
    private final FamilyPlanItemRepository familyPlanItemRepository;
    private final QuestRepository questRepository;
    private final ActivityRepository activityRepository;

    public QuestsRankingExternalService(
            FamilyPlanRepository familyPlanRepository,
            FamilyPlanItemRepository familyPlanItemRepository,
            QuestRepository questRepository,
            ActivityRepository activityRepository
    ) {
        this.familyPlanRepository = familyPlanRepository;
        this.familyPlanItemRepository = familyPlanItemRepository;
        this.questRepository = questRepository;
        this.activityRepository = activityRepository;
    }

    public int countCompletedFamilyPlans(Long familyId) {
        return (int) familyPlanRepository.findByFamilyId(familyId).stream()
                .filter(plan -> plan.getStatus() == FamilyPlanStatus.COMPLETED)
                .count();
    }

    public int countCompletedFamilyPlansSince(Long familyId, OffsetDateTime since) {
        return (int) familyPlanRepository.findByFamilyId(familyId).stream()
                .filter(plan -> plan.getStatus() == FamilyPlanStatus.COMPLETED)
                .filter(plan -> plan.getCompletedAt() != null && plan.getCompletedAt().isAfter(since))
                .count();
    }

    public List<CompletedQuestSummary> findCompletedQuestsSince(Long familyId, OffsetDateTime since) {
        return familyPlanRepository.findByFamilyId(familyId).stream()
                .filter(plan -> plan.getStatus() == FamilyPlanStatus.COMPLETED)
                .filter(plan -> plan.getCompletedAt() != null && plan.getCompletedAt().isAfter(since))
                .flatMap(plan -> familyPlanItemRepository.findByFamilyPlanId(plan.getId()).stream()
                        .map(item -> questRepository.findById(item.getQuestId())
                                .map(quest -> new CompletedQuestSummary(
                                        quest.getTitle(),
                                        plan.getCompletedAt(),
                                        quest.getEcopoints(),
                                        activityRepository.countByQuestId(quest.getId())
                                ))))
                .flatMap(java.util.Optional::stream)
                .toList();
    }
}
