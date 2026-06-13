package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;

import java.util.Optional;

@Service
public class ActivityUserCommandServiceImpl implements ActivityUserCommandService {

    private final ActivityUserRepository activityUserRepository;
    private final ActivityRepository activityRepository;

    public ActivityUserCommandServiceImpl(ActivityUserRepository activityUserRepository,
                                           ActivityRepository activityRepository) {
        this.activityUserRepository = activityUserRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public Long handle(CreateActivityUserCommand command) {
        if (!activityRepository.existsById(command.activityId()))
            throw new IllegalArgumentException("Activity with id " + command.activityId() + " not found");
        if (activityUserRepository.findByUserIdAndActivityId(command.userId(), command.activityId()).isPresent())
            throw new IllegalArgumentException("Activity user already exists for this user and activity");

        var activityUser = new ActivityUser(command.userId(), command.activityId());
        return activityUserRepository.save(activityUser).getId();
    }

    @Override
    public Optional<ActivityUser> handle(UpdateActivityUserCommand command) {
        var result = activityUserRepository.findById(command.id());
        if (result.isEmpty()) return Optional.empty();
        var activityUser = result.get();
        activityUser.updateInformation(
                command.progress(), command.endDate(), command.collaborativeSessionId()
        );
        return Optional.of(activityUserRepository.save(activityUser));
    }

    @Override
    public void handle(DeleteActivityUserCommand command) {
        if (!activityUserRepository.existsById(command.id()))
            throw new IllegalArgumentException("ActivityUser with id " + command.id() + " not found");
        activityUserRepository.deleteById(command.id());
    }
}
