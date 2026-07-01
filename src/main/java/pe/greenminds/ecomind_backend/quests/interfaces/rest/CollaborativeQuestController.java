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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.greenminds.ecomind_backend.quests.application.commandservices.CollabQuestSessionCommandService;
import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionQueryService;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetCollabQuestSessionStateQuery;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CollabQuestSessionResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CollabQuestSessionStateResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateCollabQuestSessionResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CollabQuestSessionResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CollabQuestSessionStateResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateCollabQuestSessionCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

@RestController
@RequestMapping(value = "/api/v1/collaborative-quests", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Collaborative Quests", description = "Collaborative quest endpoints")
public class CollaborativeQuestController {
    private final CollabQuestSessionCommandService collabQuestSessionCommandService;
    private final CollabQuestSessionQueryService collabQuestSessionQueryService;

    public CollaborativeQuestController(
            CollabQuestSessionCommandService collabQuestSessionCommandService,
            CollabQuestSessionQueryService collabQuestSessionQueryService
    ) {
        this.collabQuestSessionCommandService = collabQuestSessionCommandService;
        this.collabQuestSessionQueryService = collabQuestSessionQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a collaborative quest session",
            description = """
                    Creates a pending collaborative session for a collaborative quest.
                    The session starts with null startedAt and completedAt dates.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Collaborative quest session created successfully",
                    content = @Content(schema = @Schema(implementation = CollabQuestSessionResource.class))
            ),
            @ApiResponse(responseCode = "404", description = "Quest not found"),
            @ApiResponse(responseCode = "409", description = "Session already exists"),
            @ApiResponse(responseCode = "422", description = "Quest is not collaborative")
    })
    public ResponseEntity<?> createSession(
            @Valid @RequestBody CreateCollabQuestSessionResource resource
    ) {
        var command =
                CreateCollabQuestSessionCommandFromResourceAssembler.toCommandFromResource(
                        resource
                );
        var result = collabQuestSessionCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CollabQuestSessionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping("/state")
    @Operation(summary = "Get collaborative quest state")
    @ApiResponse(
            responseCode = "200",
            description = "Collaborative quest state retrieved successfully",
            content = @Content(
                    schema = @Schema(implementation = CollabQuestSessionStateResource.class)
            )
    )
    public ResponseEntity<?> getState(
            @RequestParam Long questId,
            @RequestParam Long userId
    ) {
        var state = collabQuestSessionQueryService.handle(
                new GetCollabQuestSessionStateQuery(questId, userId)
        );

        return ResponseEntity.ok(
                CollabQuestSessionStateResourceAssembler.toResource(state)
        );
    }
}
