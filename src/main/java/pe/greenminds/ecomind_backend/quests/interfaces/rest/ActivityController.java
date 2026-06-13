package pe.greenminds.ecomind_backend.quests.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivitiesByQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllActivitiesQuery;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityCommandService;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityQueryService;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateActivityResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.ActivityResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateActivityCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.UpdateActivityCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.MessageResource;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET,
                RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/activities", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Activities", description = "Activity Management Endpoints")
public class ActivityController {
    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;

    public ActivityController(ActivityCommandService activityCommandService, ActivityQueryService activityQueryService) {
        this.activityCommandService = activityCommandService;
        this.activityQueryService = activityQueryService;
    }

    @PostMapping
    public ResponseEntity<ActivityResource> createActivity(@Valid @RequestBody CreateActivityResource resource) {
        var createActivityCommand = CreateActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var activityId = activityCommandService.handle(createActivityCommand);
        var getActivityByIdQuery = new GetActivityByIdQuery(activityId);
        var activity = activityQueryService.handle(getActivityByIdQuery);
        if (activity.isPresent()) {
            var activityResource = ActivityResourceFromEntityAssembler.toResourceFromEntity(activity.get());
            return new ResponseEntity<>(activityResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ActivityResource>> getAllActivities() {
        var getAllActivitiesQuery = new GetAllActivitiesQuery();
        var activities = activityQueryService.handle(getAllActivitiesQuery);
        var activityResources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(activityResources);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResource> getActivityById(@PathVariable Long activityId) {
        var getActivityByIdQuery = new GetActivityByIdQuery(activityId);
        var activity = activityQueryService.handle(getActivityByIdQuery);
        if (activity.isPresent()) {
            var activityResource = ActivityResourceFromEntityAssembler.toResourceFromEntity(activity.get());
            return ResponseEntity.ok(activityResource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/quest/{questId}")
    public ResponseEntity<List<ActivityResource>> getActivitiesByQuestId(@PathVariable Long questId) {
        var getActivitiesByQuestIdQuery = new GetActivitiesByQuestIdQuery(questId);
        var activities = activityQueryService.handle(getActivitiesByQuestIdQuery);
        var activityResources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(activityResources);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResource> updateActivity(@PathVariable Long activityId, @Valid @RequestBody ActivityResource resource) {
        var updateActivityCommand = UpdateActivityCommandFromResourceAssembler.toCommandFromResource(activityId, resource);
        var activity = activityCommandService.handle(updateActivityCommand);
        if (activity.isPresent()) {
            var activityResource = ActivityResourceFromEntityAssembler.toResourceFromEntity(activity.get());
            return ResponseEntity.ok(activityResource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<MessageResource> deleteActivity(@PathVariable Long activityId) {
        activityCommandService.handle(new DeleteActivityCommand(activityId));
        return ResponseEntity.ok(new MessageResource("Activity with id " + activityId + " deleted successfully"));
    }
}
