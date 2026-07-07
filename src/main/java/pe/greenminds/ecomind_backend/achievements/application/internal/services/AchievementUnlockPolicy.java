package pe.greenminds.ecomind_backend.achievements.application.internal.services;

import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;

@Component
public class AchievementUnlockPolicy {
    public boolean isUnlocked(
            AchievementEntity achievement,
            int totalEcopoints,
            int completedQuests,
            int completedFamilyPlans,
            int communityMembers,
            int communityPosts,
            int communityEvents,
            int position
    ) {
        var threshold = achievement.getThresholdValue() == null ? 0 : achievement.getThresholdValue();
        return switch (achievement.getType()) {
            case FIRST_QUEST_COMPLETED -> completedQuests >= 1;
            case QUESTS_COMPLETED_COUNT -> completedQuests >= threshold;
            case FIRST_FAMILY_PLAN -> completedFamilyPlans >= 1;
            case FAMILY_PLANS_COUNT -> completedFamilyPlans >= threshold;
            case COMMUNITY_MEMBERS_COUNT -> communityMembers >= threshold;
            case COMMUNITY_POSTS_COUNT -> communityPosts >= threshold;
            case COMMUNITY_EVENTS_COUNT -> communityEvents >= threshold;
            case ECOPOINTS_THRESHOLD -> totalEcopoints >= threshold;
            case WEEKLY_TOP3 -> position >= 1 && position <= 3;
        };
    }
}
