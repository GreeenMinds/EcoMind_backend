package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityUserCommand;

import java.util.Optional;

public interface ActivityUserCommandService {
    Long handle(CreateActivityUserCommand command);
    Optional<ActivityUser> handle(UpdateActivityUserCommand command);
    void handle(DeleteActivityUserCommand command);
}
