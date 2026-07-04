package pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects;

import java.util.List;

public record FamilyReport(
        Long familyId,
        boolean hasData,
        int completedQuestsThisWeek,
        List<CompletedQuestSummary> completedQuests,
        int achievementsEarned,
        int totalAchievements
) {}
