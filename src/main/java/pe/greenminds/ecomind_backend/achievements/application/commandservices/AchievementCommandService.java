package pe.greenminds.ecomind_backend.achievements.application.commandservices;

import pe.greenminds.ecomind_backend.achievements.domain.model.commands.CreateAchievementCommand;

public interface AchievementCommandService {
    Long handle(CreateAchievementCommand command);
}
