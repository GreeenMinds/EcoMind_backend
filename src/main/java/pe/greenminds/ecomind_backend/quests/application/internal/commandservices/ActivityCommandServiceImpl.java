package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.UpdateActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityCommandService;

import java.util.Comparator;
import java.util.Optional;

@Service
public class ActivityCommandServiceImpl implements ActivityCommandService {
    private final ActivityRepository activityRepository;
    private final QuestRepository questRepository;

    public ActivityCommandServiceImpl(ActivityRepository activityRepository, QuestRepository questRepository) {
        this.activityRepository = activityRepository;
        this.questRepository = questRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateActivityCommand command) {
        if (!questRepository.existsById(command.questId()))
            throw new IllegalArgumentException("Quest with id " + command.questId() + " not found");

        var activities = activityRepository.findByQuestIdOrderByPositionAsc(command.questId());
        int finalPosition = Math.min(activities.size() + 1, command.position());

        activities.stream()
                .filter(a -> a.getPosition() >= finalPosition)
                .sorted(Comparator.comparing(Activity::getPosition).reversed())
                .forEach(a -> {
                    a.setPosition(a.getPosition() + 1);
                    activityRepository.save(a);
                });

        var activity = new Activity(command.questId(), command.description(), finalPosition, command.type(), command.image());
        return activityRepository.save(activity).getId();
    }

    @Override
    @Transactional
    public Optional<Activity> handle(UpdateActivityCommand command) {
        var result = activityRepository.findById(command.activityId());
        if (result.isEmpty()) return Optional.empty();
        var activity = result.get();
        activity.updateInformation(command.questId(), command.description(), command.position(), command.type(), command.image());
        return Optional.of(activityRepository.save(activity));
    }

    @Override
    public void handle(DeleteActivityCommand command) {
        if (!activityRepository.existsById(command.activityId()))
            throw new IllegalArgumentException("Activity with id " + command.activityId() + " not found");
        activityRepository.deleteById(command.activityId());
    }
}
