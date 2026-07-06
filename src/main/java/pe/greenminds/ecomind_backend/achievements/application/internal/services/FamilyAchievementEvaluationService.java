package pe.greenminds.ecomind_backend.achievements.application.internal.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.QuestAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.RankingAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.domain.model.valueobjects.AchievementType;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.FamilyAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.FamilyAchievementRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class FamilyAchievementEvaluationService {
    private static final Set<AchievementType> FAMILY_ACHIEVEMENT_TYPES = EnumSet.of(
            AchievementType.FIRST_FAMILY_PLAN,
            AchievementType.FAMILY_PLANS_COUNT,
            AchievementType.WEEKLY_TOP3
    );

    private final AchievementRepository achievementRepository;
    private final FamilyAchievementRepository familyAchievementRepository;
    private final QuestAchievementExternalService questAchievementExternalService;
    private final RankingAchievementExternalService rankingAchievementExternalService;
    private final AchievementUnlockPolicy achievementUnlockPolicy;

    public FamilyAchievementEvaluationService(
            AchievementRepository achievementRepository,
            FamilyAchievementRepository familyAchievementRepository,
            QuestAchievementExternalService questAchievementExternalService,
            RankingAchievementExternalService rankingAchievementExternalService,
            AchievementUnlockPolicy achievementUnlockPolicy
    ) {
        this.achievementRepository = achievementRepository;
        this.familyAchievementRepository = familyAchievementRepository;
        this.questAchievementExternalService = questAchievementExternalService;
        this.rankingAchievementExternalService = rankingAchievementExternalService;
        this.achievementUnlockPolicy = achievementUnlockPolicy;
    }

    public List<FamilyAchievementEntity> evaluateAndUnlock(Long familyId) {
        var completedFamilyPlans = questAchievementExternalService.countCompletedFamilyPlans(
                familyId
        );
        var position = rankingAchievementExternalService.findFamilyPositionOrDefault(
                familyId,
                Integer.MAX_VALUE
        );

        var familyAchievements = achievementRepository.findAll().stream()
                .filter(achievement -> FAMILY_ACHIEVEMENT_TYPES.contains(achievement.getType()))
                .toList();
        var familyAchievementIds = familyAchievements.stream()
                .map(AchievementEntity::getId)
                .collect(java.util.stream.Collectors.toSet());

        for (AchievementEntity achievement : familyAchievements) {
            if (familyAchievementRepository.existsByFamilyIdAndAchievementId(familyId, achievement.getId())) {
                continue;
            }
            if (achievementUnlockPolicy.isUnlocked(
                    achievement,
                    0,
                    0,
                    completedFamilyPlans,
                    0,
                    0,
                    0,
                    position
            )) {
                familyAchievementRepository.save(new FamilyAchievementEntity(familyId, achievement.getId()));
            }
        }

        return familyAchievementRepository.findByFamilyId(familyId).stream()
                .filter(achievement -> familyAchievementIds.contains(achievement.getAchievementId()))
                .toList();
    }

    @Transactional
    public void markAchievementsAsSeen(List<FamilyAchievementEntity> achievements) {
        achievements.stream()
                .filter(achievement -> Boolean.TRUE.equals(achievement.getNewlyUnlocked()))
                .forEach(achievement -> {
                    achievement.markSeen();
                    familyAchievementRepository.save(achievement);
                });
    }
}
