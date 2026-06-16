package pe.greenminds.ecomind_backend.profile.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.profile.application.commandservices.FamilyCommandService;
import pe.greenminds.ecomind_backend.profile.application.queryservices.FamilyQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.DeleteFamilyCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.UpdateFamilyCommitmentCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetAllFamiliesQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetFamilyByIdQuery;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.*;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/family", produces = MediaType.APPLICATION_JSON_VALUE)
public class FamilyController {
    private final FamilyCommandService commandService;
    private final FamilyQueryService queryService;

    public FamilyController(FamilyCommandService commandService, FamilyQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    public ResponseEntity<List<FamilyResource>> getAllFamilies() {
        return ResponseEntity.ok(queryService.handle(new GetAllFamiliesQuery()).stream()
                .map(FamilyResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<?> getFamilyById(@PathVariable Long familyId) {
        var family = queryService.handle(new GetFamilyByIdQuery(familyId));
        if (family.isEmpty()) return ErrorResponseAssembler.toErrorResponseFromApplicationError(ApplicationError.notFound("Family", familyId.toString()));
        return ResponseEntity.ok(FamilyResourceFromEntityAssembler.toResourceFromEntity(family.get()));
    }

    @PostMapping
    public ResponseEntity<?> createFamily(@Valid @RequestBody CreateFamilyResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(CreateFamilyCommandFromResourceAssembler.toCommandFromResource(resource)),
                FamilyResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{familyId}")
    public ResponseEntity<?> updateFamily(@PathVariable Long familyId, @Valid @RequestBody UpdateFamilyResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(UpdateFamilyCommandFromResourceAssembler.toCommandFromResource(familyId, resource)),
                FamilyResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{familyId}")
    public ResponseEntity<?> deleteFamily(@PathVariable Long familyId) {
        var result = commandService.handle(new DeleteFamilyCommand(familyId));
        return switch (result) {
            case Result.Success<Family, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<Family, ApplicationError> failure -> ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }

    @PatchMapping("/{familyId}/commitment")
    public ResponseEntity<?> updateCommitment(@PathVariable Long familyId, @RequestBody CommitmentResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new UpdateFamilyCommitmentCommand(familyId, resource.commitment())),
                FamilyResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }
}
