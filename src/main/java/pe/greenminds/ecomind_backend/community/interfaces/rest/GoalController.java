package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.community.application.commandservices.GoalCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.GoalQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetAllGoalsQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetGoalByIdQuery;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateGoalResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.GoalResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdateGoalResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreateGoalCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.GoalResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.UpdateGoalCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/goals", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Goals", description = "Community goal catalog management endpoints")
public class GoalController {

    private final GoalCommandService goalCommandService;
    private final GoalQueryService goalQueryService;

    public GoalController(GoalCommandService goalCommandService, GoalQueryService goalQueryService) {
        this.goalCommandService = goalCommandService;
        this.goalQueryService = goalQueryService;
    }

    @GetMapping
    public ResponseEntity<List<GoalResource>> getAllGoals() {
        var resources = goalQueryService.handle(new GetAllGoalsQuery())
                .stream()
                .map(GoalResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResource> getGoalById(@PathVariable Long id) {
        return goalQueryService.handle(new GetGoalByIdQuery(id))
                .map(GoalResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createGoal(@Valid @RequestBody CreateGoalResource resource) {
        var command = CreateGoalCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = goalCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GoalResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGoal(@PathVariable Long id, @Valid @RequestBody UpdateGoalResource resource) {
        var command = UpdateGoalCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = goalCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                GoalResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long id) {
        var result = goalCommandService.handle(new DeleteGoalCommand(id));

        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();

            case Result.Failure<Void, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}