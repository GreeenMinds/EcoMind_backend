package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.application.commandservices.ActivityCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.CreateActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.QuestRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.util.Comparator;

@Service
public class ActivityCommandServiceImpl implements ActivityCommandService {
    private final ActivityRepository activityRepository;
    private final QuestRepository questRepository;

    public ActivityCommandServiceImpl(final ActivityRepository activityRepository,
    final QuestRepository questRepository) {
        this.activityRepository = activityRepository;
        this.questRepository = questRepository;
    }

    @Override
    @Transactional
    public Result<Activity, ApplicationError> handle(CreateActivityCommand command){
        if(!questRepository.existsById(command.questId())){
            return Result.failure(
                    ApplicationError.notFound("Quest", command.questId().toString())
            );
        }
        try{
            var activities = activityRepository.findByQuestsIdOrderByOrderAsc(command.questId());
            int finalOrder = Math.min(activities.size() +1, command.order());

            //Desplazar las existentes en la quest
            activities.stream()
                    .filter(activity -> activity.getOrder() >= finalOrder)
                    .sorted(Comparator.comparing(Activity::getOrder).reversed())
                    .forEach(activity -> {
                        activity.setOrder(activity.getOrder() + 1);
                        activityRepository.save(activity);
                    });

            var activity = new Activity(
                    command.questId(),
                    command.description(),
                    finalOrder,
                    command.type(),
                    command.image()
            );
            return Result.success(activityRepository.save(activity));
        } catch(IllegalArgumentException e){
            return Result.failure(ApplicationError.validationError("Activity", e.getMessage()));
        } catch(Exception e){
            return Result.failure(
                    ApplicationError.unexpected("Activity creation", e.getMessage())
            );
        }
    }
}
