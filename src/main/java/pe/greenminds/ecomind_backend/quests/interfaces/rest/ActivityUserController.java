package pe.greenminds.ecomind_backend.quests.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeleteActivityUserCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByActivityIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByUserIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllActivityUsersQuery;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityUserCommandService;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityUserQueryService;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityUserResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateActivityUserResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.ActivityUserResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateActivityUserCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.UpdateActivityUserCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.resources.MessageResource;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET,
                RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/activity-users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Activity Users", description = "Activity User Management Endpoints")
public class ActivityUserController {

    private final ActivityUserCommandService activityUserCommandService;
    private final ActivityUserQueryService activityUserQueryService;

    public ActivityUserController(ActivityUserCommandService activityUserCommandService,
                                   ActivityUserQueryService activityUserQueryService) {
        this.activityUserCommandService = activityUserCommandService;
        this.activityUserQueryService = activityUserQueryService;
    }

    @PostMapping
    public ResponseEntity<ActivityUserResource> createActivityUser(@Valid @RequestBody CreateActivityUserResource resource) {
        var createActivityUserCommand = CreateActivityUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var activityUserId = activityUserCommandService.handle(createActivityUserCommand);
        var getActivityUserByIdQuery = new GetActivityUserByIdQuery(activityUserId);
        var activityUser = activityUserQueryService.handle(getActivityUserByIdQuery);
        if (activityUser.isPresent()) {
            var activityUserResource = ActivityUserResourceFromEntityAssembler.toResourceFromEntity(activityUser.get());
            return new ResponseEntity<>(activityUserResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ActivityUserResource>> getAllActivityUsers() {
        var getAllActivityUsersQuery = new GetAllActivityUsersQuery();
        var activityUsers = activityUserQueryService.handle(getAllActivityUsersQuery);
        var activityUserResources = activityUsers.stream()
                .map(ActivityUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(activityUserResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityUserResource> getActivityUserById(@PathVariable Long id) {
        var getActivityUserByIdQuery = new GetActivityUserByIdQuery(id);
        var activityUser = activityUserQueryService.handle(getActivityUserByIdQuery);
        if (activityUser.isPresent()) {
            var activityUserResource = ActivityUserResourceFromEntityAssembler.toResourceFromEntity(activityUser.get());
            return ResponseEntity.ok(activityUserResource);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ActivityUserResource>> getActivityUsersByUserId(@PathVariable Long userId) {
        var getActivityUserByUserIdQuery = new GetActivityUserByUserIdQuery(userId);
        var activityUsers = activityUserQueryService.handle(getActivityUserByUserIdQuery);
        var activityUserResources = activityUsers.stream()
                .map(ActivityUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(activityUserResources);
    }

    @GetMapping("/by-activity/{activityId}")
    public ResponseEntity<List<ActivityUserResource>> getActivityUsersByActivityId(@PathVariable Long activityId) {
        var getActivityUserByActivityIdQuery = new GetActivityUserByActivityIdQuery(activityId);
        var activityUsers = activityUserQueryService.handle(getActivityUserByActivityIdQuery);
        var activityUserResources = activityUsers.stream()
                .map(ActivityUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(activityUserResources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityUserResource> updateActivityUser(@PathVariable Long id, @Valid @RequestBody ActivityUserResource resource) {
        var updateActivityUserCommand = UpdateActivityUserCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var activityUser = activityUserCommandService.handle(updateActivityUserCommand);
        if (activityUser.isPresent()) {
            var activityUserResource = ActivityUserResourceFromEntityAssembler.toResourceFromEntity(activityUser.get());
            return ResponseEntity.ok(activityUserResource);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResource> deleteActivityUser(@PathVariable Long id) {
        activityUserCommandService.handle(new DeleteActivityUserCommand(id));
        return ResponseEntity.ok(new MessageResource("ActivityUser with id " + id + " deleted successfully"));
    }
}
