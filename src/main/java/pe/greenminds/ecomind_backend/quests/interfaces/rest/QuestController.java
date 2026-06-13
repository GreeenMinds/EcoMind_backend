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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.greenminds.ecomind_backend.quests.application.commandservices.QuestCommandService;
import pe.greenminds.ecomind_backend.quests.application.queryservices.QuestQueryService;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CreateQuestResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.QuestResource;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.CreateQuestCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.transform.QuestResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

@RestController
@RequestMapping(value="/api/v1/quests", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name="Quests", description = "Quest management endpoints")
public class QuestController {
    private final QuestCommandService questCommandService;
    private final QuestQueryService questQueryService;

    public QuestController(QuestCommandService questCommandService, QuestQueryService questQueryService) {
        this.questCommandService = questCommandService;
        this.questQueryService = questQueryService;
    }

    @PostMapping
    @Operation(
       summary = "Create a new quest",
       description = "Creates a new quest with all neccesary information."
    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "201",
                    description = "Quest created succesfully",
                    content = @Content(schema = @Schema(implementation = QuestResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: quest already exists")
    })
    public ResponseEntity<?> createQuest(@Valid @RequestBody CreateQuestResource resource){
        var createQuestCommand = CreateQuestCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = questCommandService.handle(createQuestCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                QuestResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
