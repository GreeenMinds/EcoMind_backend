package pe.greenminds.ecomind_backend.quests.domain.services;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityCommand;

import java.util.Optional;

public interface ActivityCommandService {
    Long handle(CreateActivityCommand command);
    Optional<Activity> handle(UpdateActivityCommand command);
    void handle(DeleteActivityCommand command);
}
