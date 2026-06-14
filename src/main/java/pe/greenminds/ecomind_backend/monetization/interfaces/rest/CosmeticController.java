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
import pe.greenminds.ecomind_backend.monetization.application.commandservices.CosmeticCommandService;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.CosmeticQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllCosmeticsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetCosmeticByIdQuery;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateCosmeticResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CosmeticResource;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CreateCosmeticCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform.CosmeticResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cosmetic", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Cosmetics", description = "Cosmetic management endpoints")
public class CosmeticController {

    private final CosmeticCommandService cosmeticCommandService;
    private final CosmeticQueryService cosmeticQueryService;

    public CosmeticController(CosmeticCommandService cosmeticCommandService, CosmeticQueryService cosmeticQueryService) {
        this.cosmeticCommandService = cosmeticCommandService;
        this.cosmeticQueryService = cosmeticQueryService;
    }

    @PostMapping
    @Operation(
            summary = "Create a new cosmetic",
            description = "Creates a new cosmetic item."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cosmetic created successfully",
                    content = @Content(schema = @Schema(implementation = CosmeticResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: cosmetic already exists")
    })
    public ResponseEntity<?> createCosmetic(@Valid @RequestBody CreateCosmeticResource resource) {
        var command = CreateCosmeticCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = cosmeticCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                CosmeticResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all cosmetics",
            description = "Retrieves all available cosmetics."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cosmetics retrieved successfully."
    )
    public ResponseEntity<List<CosmeticResource>> getAllCosmetics() {
        var cosmetics = cosmeticQueryService.handle(new GetAllCosmeticsQuery());

        var resources = cosmetics.stream()
                .map(CosmeticResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{cosmeticId}")
    @Operation(
            summary = "Get cosmetic by ID",
            description = "Retrieves a cosmetic using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Cosmetic found",
                    content = @Content(schema = @Schema(implementation = CosmeticResource.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cosmetic not found"
            )
    })
    public ResponseEntity<?> getCosmeticById(@PathVariable Long cosmeticId) {
        var cosmetic = cosmeticQueryService.handle(new GetCosmeticByIdQuery(cosmeticId));
        if (cosmetic.isEmpty()) {
            var error = ApplicationError.notFound("Cosmetic", cosmeticId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }
        var resource = CosmeticResourceFromEntityAssembler.toResourceFromEntity(cosmetic.get());
        return ResponseEntity.ok(resource);
    }
}
