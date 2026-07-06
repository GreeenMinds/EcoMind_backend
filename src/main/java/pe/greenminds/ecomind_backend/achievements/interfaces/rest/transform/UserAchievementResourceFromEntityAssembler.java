package pe.greenminds.ecomind_backend.achievements.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.UserAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources.UserAchievementResource;

public class UserAchievementResourceFromEntityAssembler {
    public static UserAchievementResource toResourceFromEntity(
            UserAchievementEntity entity,
            AchievementEntity achievement
    ) {
        return new UserAchievementResource(
                entity.getId(),
                entity.getUserId(),
                entity.getAchievementId(),
                achievement != null ? achievement.getName() : null,
                achievement != null ? achievement.getDescription() : null,
                entity.getEarnedAt(),
                entity.getNewlyUnlocked()
        );
    }
}
