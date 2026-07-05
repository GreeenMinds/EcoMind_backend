package pe.greenminds.ecomind_backend.learning.interfaces.rest;

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
import pe.greenminds.ecomind_backend.learning.application.commandservices.TutorialProgressCommandService;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CompleteTutorialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.SkipTutorialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetTutorialProgressByUserQuery;
import pe.greenminds.ecomind_backend.learning.domain.repositories.TutorialProgressRepository;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.CompleteTutorialStepResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.TutorialProgressResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.CompleteTutorialStepCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.TutorialProgressResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

@RestController
@RequestMapping(value = "/api/v1/tutorials", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Tutorial", description = "Tutorial progress management endpoints")
public class TutorialController {
    private final TutorialProgressCommandService tutorialProgressCommandService;
    private final TutorialProgressRepository tutorialProgressRepository;

    public TutorialController(
            TutorialProgressCommandService tutorialProgressCommandService,
            TutorialProgressRepository tutorialProgressRepository
    ) {
        this.tutorialProgressCommandService = tutorialProgressCommandService;
        this.tutorialProgressRepository = tutorialProgressRepository;
    }

    @PostMapping("/progress/step")
    @Operation(
            summary = "Complete a tutorial step",
            description = "Advances the tutorial progress by one step for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tutorial step completed successfully",
                    content = @Content(schema = @Schema(implementation = TutorialProgressResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Tutorial progress not found")
    })
    public ResponseEntity<?> completeStep(@Valid @RequestBody CompleteTutorialStepResource resource) {
        var command = CompleteTutorialStepCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = tutorialProgressCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                TutorialProgressResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/progress/complete")
    @Operation(
            summary = "Complete tutorial",
            description = "Marks the tutorial as completed for a user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tutorial completed successfully",
                    content = @Content(schema = @Schema(implementation = TutorialProgressResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Tutorial progress not found")
    })
    public ResponseEntity<?> completeTutorial(@Valid @RequestBody CompleteTutorialStepResource resource) {
        var command = new CompleteTutorialCommand(resource.userId());
        var result = tutorialProgressCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                TutorialProgressResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @PostMapping("/progress/skip")
    @Operation(
            summary = "Skip tutorial",
            description = "Skips the tutorial for a user, marking it as skipped and completed."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Tutorial skipped successfully",
                    content = @Content(schema = @Schema(implementation = TutorialProgressResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Tutorial progress not found")
    })
    public ResponseEntity<?> skipTutorial(@Valid @RequestBody CompleteTutorialStepResource resource) {
        var command = new SkipTutorialCommand(resource.userId());
        var result = tutorialProgressCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                TutorialProgressResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/progress")
    @Operation(
            summary = "Get tutorial progress",
            description = "Retrieves the current tutorial progress for a user."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Tutorial progress found",
                    content = @Content(schema = @Schema(implementation = TutorialProgressResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Tutorial progress not found"
            )
    })
    public ResponseEntity<?> getProgress(@RequestParam Long userId) {
        var progress = tutorialProgressRepository.findByUserId(userId);
        if (progress.isEmpty()) {
            var error = ApplicationError.notFound(
                    "TutorialProgress",
                    userId.toString()
            );
            return ErrorResponseAssembler
                    .toErrorResponseFromApplicationError(error);
        }
        var resource = TutorialProgressResourceFromEntityAssembler
                .toResourceFromEntity(progress.get());
        return ResponseEntity.ok(resource);
    }
}
