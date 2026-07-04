package pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.FamilyReport;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CompletedQuestResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.FamilyReportResource;

public class FamilyReportResourceFromReportAssembler {
    public static FamilyReportResource toResourceFromReport(FamilyReport report) {
        var completedQuests = report.completedQuests().stream()
                .map(summary -> new CompletedQuestResource(
                        summary.questTitle(),
                        summary.completedAt() != null ? summary.completedAt().toString() : null,
                        summary.ecopoints(),
                        summary.activityCount()
                ))
                .toList();

        return new FamilyReportResource(
                report.familyId(),
                report.hasData(),
                report.completedQuestsThisWeek(),
                completedQuests,
                report.achievementsEarned(),
                report.totalAchievements()
        );
    }
}
