package pe.greenminds.ecomind_backend.achievements.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.application.internal.services.FamilyRankingService;

@Service
public class RankingAchievementExternalService {
    private final FamilyRankingService familyRankingService;

    public RankingAchievementExternalService(FamilyRankingService familyRankingService) {
        this.familyRankingService = familyRankingService;
    }

    public int findFamilyPositionOrDefault(Long familyId, int defaultPosition) {
        return familyRankingService.findFamilyPosition(familyId)
                .map(entry -> entry.position())
                .orElse(defaultPosition);
    }
}
