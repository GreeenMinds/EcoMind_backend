package pe.greenminds.ecomind_backend.monetization.interfaces.rest;

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
import pe.greenminds.ecomind_backend.monetization.application.commandservices.MultiplierCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.MultiplierQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllMultipliersQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetMultiplierByIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateMultiplierResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.MultiplierResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateMultiplierCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.MultiplierResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/multiplier", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Multipliers", description = "Multiplier management endpoints")
public class MultiplierController {

    private final MultiplierCommandService multiplierCommandService;
    private final MultiplierQueryService multiplierQueryService;

    public MultiplierController(MultiplierCommandService multiplierCommandService, MultiplierQueryService multiplierQueryService) {
        this.multiplierCommandService = multiplierCommandService;
        this.multiplierQueryService = multiplierQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new multiplier",
            description = "Creates a new multiplier with all necessary information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Multiplier created successfully",
                    content = @Content(schema = @Schema(implementation = MultiplierResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: multiplier already exists")
    })
    public ResponseEntity<?> createMultiplier(@Valid @RequestBody CreateMultiplierResource resource) {
        var command = CreateMultiplierCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = multiplierCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MultiplierResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all multipliers",
            description = "Retrieves all available multipliers."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Multipliers retrieved successfully."
    )
    public ResponseEntity<List<MultiplierResource>> getAllMultipliers() {
        var multipliers = multiplierQueryService.handle(new GetAllMultipliersQuery());

        var resources = multipliers.stream()
                .map(MultiplierResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{multiplierId}")
    @Operation(
            summary = "Get multiplier by ID",
            description = "Retrieves a multiplier using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Multiplier found",
                    content = @Content(schema = @Schema(implementation = MultiplierResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Multiplier not found"
            )
    })
    public ResponseEntity<?> getMultiplierById(@PathVariable Long multiplierId) {
        var multiplier = multiplierQueryService.handle(new GetMultiplierByIdQuery(multiplierId));
        if (multiplier.isEmpty()) {
            var error = ApplicationError.notFound("Multiplier", multiplierId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = MultiplierResourceFromEntityAssembler.toResourceFromEntity(multiplier.get());
        return ResponseEntity.ok(resource);
    }
}
