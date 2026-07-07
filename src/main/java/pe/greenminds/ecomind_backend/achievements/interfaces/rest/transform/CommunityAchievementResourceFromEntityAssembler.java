package pe.greenminds.ecomind_backend.achievements.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.CommunityAchievementEntity;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources.CommunityAchievementResource;

public class CommunityAchievementResourceFromEntityAssembler {
    public static CommunityAchievementResource toResourceFromEntity(
            CommunityAchievementEntity entity,
            AchievementEntity achievement
    ) {
        return new CommunityAchievementResource(
                entity.getId(),
                entity.getCommunityId(),
                entity.getAchievementId(),
                achievement != null ? achievement.getName() : null,
                achievement != null ? achievement.getDescription() : null,
                entity.getEarnedAt(),
                entity.getNewlyUnlocked()
        );
    }
}
