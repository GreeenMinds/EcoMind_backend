package pe.greenminds.ecomind_backend.achievements.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities.AchievementEntity;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources.AchievementResource;

public class AchievementResourceFromEntityAssembler {
    public static AchievementResource toResourceFromEntity(AchievementEntity entity) {
        return new AchievementResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getType().name(),
                entity.getThresholdValue()
        );
    }
}
