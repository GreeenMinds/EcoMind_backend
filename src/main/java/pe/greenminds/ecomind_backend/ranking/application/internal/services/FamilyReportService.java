package pe.greenminds.ecomind_backend.ranking.application.internal.services;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.FamilyAchievementRepository;
import pe.greenminds.ecomind_backend.ranking.application.outboundservices.external.QuestsRankingExternalService;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.FamilyReport;

import java.time.OffsetDateTime;

@Service
public class FamilyReportService {

    private final QuestsRankingExternalService questsRankingExternalService;
    private final FamilyAchievementRepository familyAchievementRepository;
    private final AchievementRepository achievementRepository;

    public FamilyReportService(
            QuestsRankingExternalService questsRankingExternalService,
            FamilyAchievementRepository familyAchievementRepository,
            AchievementRepository achievementRepository
    ) {
        this.questsRankingExternalService = questsRankingExternalService;
        this.familyAchievementRepository = familyAchievementRepository;
        this.achievementRepository = achievementRepository;
    }

    public FamilyReport generateWeeklyReport(Long familyId) {
        var since = OffsetDateTime.now().minusDays(7);
        var completedQuests = questsRankingExternalService.findCompletedQuestsSince(familyId, since);
        var achievementsEarned = familyAchievementRepository.findByFamilyId(familyId).size();
        var totalAchievements = (int) achievementRepository.count();

        if (completedQuests.isEmpty()) {
            return new FamilyReport(familyId, false, 0, completedQuests, achievementsEarned, totalAchievements);
        }

        return new FamilyReport(
                familyId,
                true,
                completedQuests.size(),
                completedQuests,
                achievementsEarned,
                totalAchievements
        );
    }
}
