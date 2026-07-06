package pe.greenminds.ecomind_backend.achievements.application.internal.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.ProfileAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.QuestAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.UserAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.UserAchievementRepository;
import pe.greenminds.ecomind_backend.achievements.domain.model.valueobjects.AchievementType;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class UserAchievementEvaluationService {
    private static final Set<AchievementType> USER_ACHIEVEMENT_TYPES = EnumSet.of(
            AchievementType.FIRST_QUEST_COMPLETED,
            AchievementType.QUESTS_COMPLETED_COUNT,
            AchievementType.ECOPOINTS_THRESHOLD
    );

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final ProfileAchievementExternalService profileAchievementExternalService;
    private final QuestAchievementExternalService questAchievementExternalService;
    private final AchievementUnlockPolicy achievementUnlockPolicy;

    public UserAchievementEvaluationService(
            AchievementRepository achievementRepository,
            UserAchievementRepository userAchievementRepository,
            ProfileAchievementExternalService profileAchievementExternalService,
            QuestAchievementExternalService questAchievementExternalService,
            AchievementUnlockPolicy achievementUnlockPolicy
    ) {
        this.achievementRepository = achievementRepository;
        this.userAchievementRepository = userAchievementRepository;
        this.profileAchievementExternalService = profileAchievementExternalService;
        this.questAchievementExternalService = questAchievementExternalService;
        this.achievementUnlockPolicy = achievementUnlockPolicy;
    }

    public List<UserAchievementEntity> evaluateAndUnlock(Long userId) {
        var totalEcopoints = profileAchievementExternalService.fetchUserEcopointsTotal(userId);
        var completedQuests = questAchievementExternalService.countCompletedAchievementQuestsByUserIds(
                List.of(userId)
        );

        var userAchievements = achievementRepository.findAll().stream()
                .filter(achievement -> USER_ACHIEVEMENT_TYPES.contains(achievement.getType()))
                .toList();
        var userAchievementIds = userAchievements.stream()
                .map(achievement -> achievement.getId())
                .collect(java.util.stream.Collectors.toSet());

        for (var achievement : userAchievements) {
            if (userAchievementRepository.existsByUserIdAndAchievementId(userId, achievement.getId())) {
                continue;
            }
            if (achievementUnlockPolicy.isUnlocked(
                    achievement,
                    totalEcopoints,
                    completedQuests,
                    0,
                    0,
                    0,
                    0,
                    Integer.MAX_VALUE
            )) {
                userAchievementRepository.save(new UserAchievementEntity(userId, achievement.getId()));
            }
        }

        return userAchievementRepository.findByUserId(userId).stream()
                .filter(achievement -> userAchievementIds.contains(achievement.getAchievementId()))
                .toList();
    }

    @Transactional
    public void markAchievementsAsSeen(List<UserAchievementEntity> achievements) {
        achievements.stream()
                .filter(achievement -> Boolean.TRUE.equals(achievement.getNewlyUnlocked()))
                .forEach(achievement -> {
                    achievement.markSeen();
                    userAchievementRepository.save(achievement);
                });
    }
}
