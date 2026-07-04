package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

import java.util.List;

public record FamilyReportResource(
        Long familyId,
        boolean hasData,
        int completedQuestsThisWeek,
        List<CompletedQuestResource> completedQuests,
        int achievementsEarned,
        int totalAchievements
) {}
