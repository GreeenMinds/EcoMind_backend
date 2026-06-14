package pe.greenminds.ecomind_backend.quests.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.quests.application.commandservices.ActivityCommandService;
import pe.greenminds.ecomind_backend.quests.application.queryservices.ActivityQueryService;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivitiesByQuestIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityByIdQuery;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.ActivityResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateActivityResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.ActivityResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateActivityCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.QuestResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Activities", description = "Activity management endpoints")
public class ActivityController {
    private final ActivityCommandService activityCommandService;
    private final ActivityQueryService activityQueryService;

    public ActivityController(ActivityCommandService activityCommandService, ActivityQueryService activityQueryService) {
        this.activityCommandService = activityCommandService;
        this.activityQueryService = activityQueryService;
    }

    @PostMapping
    @Operation(
            summary="Create a new activity",
            description="Creates a new activity linked to a quest"
    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "201",
                    description = "Activity created succesfully",
                    content = @Content(schema = @Schema(implementation = ActivityResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: activity already exists")
    })
    public ResponseEntity<?> createActivity(@Valid @RequestBody CreateActivityResource resource) {
        var createActivityCommand = CreateActivityCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = activityCommandService.handle(createActivityCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                ActivityResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{activityId}")
    @Operation(
            summary = "Get activity by Id",
            description = "Retrieve an activity using it's uniques identifier,"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Activity found",
                    content = @Content(
                            schema = @Schema(implementation = ActivityResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Activity not found"
            )
    })
    public ResponseEntity<?> getActivityById(@PathVariable Long activityId) {
        var activity  = activityQueryService.handle(
                new GetActivityByIdQuery(activityId)
        );
        if(activity.isEmpty()){
            var error = ApplicationError.notFound(
                    "Activity",
                    activityId.toString()
            );
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = ActivityResourceFromEntityAssembler.toResourceFromEntity(activity.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/quest/{questId}")
    @Operation(
            summary = "Get all activities from one quest",
            description = "Retrieves all available activities in a quest."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Activities retrieved succesfully."
    )
    public ResponseEntity<List<ActivityResource>> getActivitiesByQuestId(@PathVariable Long questId) {
        var activities  = activityQueryService.handle(new GetActivitiesByQuestIdQuery(questId));

        var resources = activities.stream()
                .map(ActivityResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}
