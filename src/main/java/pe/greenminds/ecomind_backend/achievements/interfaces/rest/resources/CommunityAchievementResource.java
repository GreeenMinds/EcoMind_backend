package pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources;

import java.util.Date;

public record CommunityAchievementResource(
        Long id,
        Long communityId,
        Long achievementId,
        String achievementName,
        String achievementDescription,
        Date earnedAt,
        Boolean newlyUnlocked
) {}
