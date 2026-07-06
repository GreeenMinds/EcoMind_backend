package pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources;

import java.util.Date;

public record UserAchievementResource(
        Long id,
        Long userId,
        Long achievementId,
        String achievementName,
        String achievementDescription,
        Date earnedAt,
        Boolean newlyUnlocked
) {}
