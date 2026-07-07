package pe.greenminds.ecomind_backend.achievements.application.internal.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.CommunityAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.ProfileAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.application.outboundservices.external.QuestAchievementExternalService;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.CommunityAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.CommunityAchievementRepository;
import pe.greenminds.ecomind_backend.achievements.domain.model.valueobjects.AchievementType;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class CommunityAchievementEvaluationService {
    private static final Set<AchievementType> COMMUNITY_ACHIEVEMENT_TYPES = EnumSet.of(
            AchievementType.COMMUNITY_MEMBERS_COUNT,
            AchievementType.COMMUNITY_POSTS_COUNT,
            AchievementType.COMMUNITY_EVENTS_COUNT
    );

    private final AchievementRepository achievementRepository;
    private final CommunityAchievementRepository communityAchievementRepository;
    private final ProfileAchievementExternalService profileAchievementExternalService;
    private final QuestAchievementExternalService questAchievementExternalService;
    private final CommunityAchievementExternalService communityAchievementExternalService;
    private final AchievementUnlockPolicy achievementUnlockPolicy;

    public CommunityAchievementEvaluationService(
            AchievementRepository achievementRepository,
            CommunityAchievementRepository communityAchievementRepository,
            ProfileAchievementExternalService profileAchievementExternalService,
            QuestAchievementExternalService questAchievementExternalService,
            CommunityAchievementExternalService communityAchievementExternalService,
            AchievementUnlockPolicy achievementUnlockPolicy
    ) {
        this.achievementRepository = achievementRepository;
        this.communityAchievementRepository = communityAchievementRepository;
        this.profileAchievementExternalService = profileAchievementExternalService;
        this.questAchievementExternalService = questAchievementExternalService;
        this.communityAchievementExternalService = communityAchievementExternalService;
        this.achievementUnlockPolicy = achievementUnlockPolicy;
    }

    public List<CommunityAchievementEntity> evaluateAndUnlock(Long communityId) {
        var communityMemberUserIds = profileAchievementExternalService.fetchCommunityMemberUserIds(
                communityId
        );
        var totalEcopoints = profileAchievementExternalService.fetchCommunityEcopointsTotal(
                communityId
        );
        var completedQuests = questAchievementExternalService.countCompletedAchievementQuestsByUserIds(
                communityMemberUserIds
        );
        var communityMembers = communityMemberUserIds.size();
        var communityPosts = communityAchievementExternalService.countPostsByCommunityId(
                communityId
        );
        var communityEvents = communityAchievementExternalService.countEventsByCommunityId(
                communityId
        );

        var communityAchievements = achievementRepository.findAll().stream()
                .filter(achievement -> COMMUNITY_ACHIEVEMENT_TYPES.contains(achievement.getType()))
                .toList();
        var communityAchievementIds = communityAchievements.stream()
                .map(achievement -> achievement.getId())
                .collect(java.util.stream.Collectors.toSet());

        for (var achievement : communityAchievements) {
            if (communityAchievementRepository.existsByCommunityIdAndAchievementId(
                    communityId,
                    achievement.getId()
            )) {
                continue;
            }
            if (achievementUnlockPolicy.isUnlocked(
                    achievement,
                    totalEcopoints,
                    completedQuests,
                    0,
                    communityMembers,
                    communityPosts,
                    communityEvents,
                    Integer.MAX_VALUE
            )) {
                communityAchievementRepository.save(
                        new CommunityAchievementEntity(communityId, achievement.getId())
                );
            }
        }

        return communityAchievementRepository.findByCommunityId(communityId).stream()
                .filter(achievement -> communityAchievementIds.contains(achievement.getAchievementId()))
                .toList();
    }

    @Transactional
    public void markAchievementsAsSeen(List<CommunityAchievementEntity> achievements) {
        achievements.stream()
                .filter(achievement -> Boolean.TRUE.equals(achievement.getNewlyUnlocked()))
                .forEach(achievement -> {
                    achievement.markSeen();
                    communityAchievementRepository.save(achievement);
                });
    }
}
