package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.greenminds.ecomind_backend.community.application.commandservices.CommunityGoalCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.CommunityGoalQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeleteCommunityGoalCommand;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetCommunityGoalByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchCommunityGoalsQuery;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CommunityGoalResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreateCommunityGoalResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.IncrementCommunityGoalProgressResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdateCommunityGoalResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdateCommunityGoalStatusResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CommunityGoalResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreateCommunityGoalCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.IncrementCommunityGoalProgressCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.UpdateCommunityGoalCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.UpdateCommunityGoalStatusCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/community-goals", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Community Goals", description = "Community goal management endpoints")
public class CommunityGoalController {

    private final CommunityGoalCommandService communityGoalCommandService;
    private final CommunityGoalQueryService communityGoalQueryService;

    public CommunityGoalController(
            CommunityGoalCommandService communityGoalCommandService,
            CommunityGoalQueryService communityGoalQueryService
    ) {
        this.communityGoalCommandService = communityGoalCommandService;
        this.communityGoalQueryService = communityGoalQueryService;
    }

    @GetMapping
    public ResponseEntity<List<CommunityGoalResource>> searchCommunityGoals(
            @RequestParam(name = "community_id", required = false) Long communityId
    ) {
        var query = new SearchCommunityGoalsQuery(communityId);

        var resources = communityGoalQueryService.handle(query)
                .stream()
                .map(CommunityGoalResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityGoalResource> getCommunityGoalById(@PathVariable Long id) {
        return communityGoalQueryService.handle(new GetCommunityGoalByIdQuery(id))
                .map(CommunityGoalResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCommunityGoal(@Valid @RequestBody CreateCommunityGoalResource resource) {
        var command = CreateCommunityGoalCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = communityGoalCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CommunityGoalResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCommunityGoal(@PathVariable Long id, @Valid @RequestBody UpdateCommunityGoalResource resource) {
        var command = UpdateCommunityGoalCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = communityGoalCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CommunityGoalResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/progress")
    public ResponseEntity<?> incrementCommunityGoalProgress(@PathVariable Long id, @Valid @RequestBody IncrementCommunityGoalProgressResource resource) {
        var command = IncrementCommunityGoalProgressCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = communityGoalCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CommunityGoalResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateCommunityGoalStatus(@PathVariable Long id, @Valid @RequestBody UpdateCommunityGoalStatusResource resource) {
        var command = UpdateCommunityGoalStatusCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = communityGoalCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CommunityGoalResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommunityGoal(@PathVariable Long id) {
        var result = communityGoalCommandService.handle(new DeleteCommunityGoalCommand(id));

        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();

            case Result.Failure<Void, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
