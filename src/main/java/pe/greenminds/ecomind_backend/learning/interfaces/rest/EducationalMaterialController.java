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
import pe.greenminds.ecomind_backend.learning.application.commandservices.EducationalMaterialCommandService;
import pe.greenminds.ecomind_backend.learning.application.queryservices.EducationalMaterialQueryService;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.DeleteEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetAllEducationalMaterialsQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetEducationalMaterialByIdQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.SearchEducationalMaterialsQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.CreateEducationalMaterialResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.EducationalMaterialResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.UpdateEducationalMaterialResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.CreateEducationalMaterialCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.EducationalMaterialResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.FavoriteMaterialEntity;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories.FavoriteMaterialPersistenceRepository;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.UpdateEducationalMaterialCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/educational-materials", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Educational Materials", description = "Educational material management endpoints")
public class EducationalMaterialController {
    private final EducationalMaterialCommandService educationalMaterialCommandService;
    private final EducationalMaterialQueryService educationalMaterialQueryService;
    private final FavoriteMaterialPersistenceRepository favoriteMaterialPersistenceRepository;

    public EducationalMaterialController(
            EducationalMaterialCommandService educationalMaterialCommandService,
            EducationalMaterialQueryService educationalMaterialQueryService,
            FavoriteMaterialPersistenceRepository favoriteMaterialPersistenceRepository
    ) {
        this.educationalMaterialCommandService = educationalMaterialCommandService;
        this.educationalMaterialQueryService = educationalMaterialQueryService;
        this.favoriteMaterialPersistenceRepository = favoriteMaterialPersistenceRepository;
    }

    @PostMapping
    @Operation(
            summary = "Create a new educational material",
            description = "Creates a new educational material with all necessary information."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Educational material created successfully",
                    content = @Content(schema = @Schema(implementation = EducationalMaterialResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict: educational material already exists")
    })
    public ResponseEntity<?> createEducationalMaterial(@Valid @RequestBody CreateEducationalMaterialResource resource) {
        var createEducationalMaterialCommand = CreateEducationalMaterialCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = educationalMaterialCommandService.handle(createEducationalMaterialCommand);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EducationalMaterialResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @Operation(
            summary = "Get all educational materials",
            description = "Retrieves all available educational materials."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Educational materials retrieved successfully."
    )
    public ResponseEntity<List<EducationalMaterialResource>> getAllEducationalMaterials() {
        var materials = educationalMaterialQueryService.handle(new GetAllEducationalMaterialsQuery());

        var resources = materials.stream()
                .map(EducationalMaterialResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{materialId}")
    @Operation(
            summary = "Get educational material by ID",
            description = "Retrieves an educational material using its unique identifier."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Educational material found",
                    content = @Content(
                            schema = @Schema(implementation = EducationalMaterialResource.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Educational material not found"
            )
    })
    public ResponseEntity<?> getEducationalMaterialById(
            @PathVariable Long materialId
    ) {
        var material = educationalMaterialQueryService.handle(
                new GetEducationalMaterialByIdQuery(materialId)
        );
        if (material.isEmpty()) {
            var error = ApplicationError.notFound(
                    "EducationalMaterial",
                    materialId.toString()
            );
            return ErrorResponseAssembler
                    .toErrorResponseFromApplicationError(error);
        }
        var resource = EducationalMaterialResourceFromEntityAssembler
                .toResourceFromEntity(material.get());
        return ResponseEntity.ok(resource);
    }

    @PutMapping("/{materialId}")
    @Operation(
            summary = "Update an educational material",
            description = "Completely updates an educational material while preserving its identity."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Educational material updated successfully",
                    content = @Content(schema = @Schema(implementation = EducationalMaterialResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Educational material not found")
    })
    public ResponseEntity<?> updateEducationalMaterial(
            @PathVariable Long materialId,
            @Valid @RequestBody UpdateEducationalMaterialResource resource
    ) {
        var command =
                UpdateEducationalMaterialCommandFromResourceAssembler.toCommandFromResource(materialId, resource);
        var result = educationalMaterialCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                EducationalMaterialResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search educational materials",
            description = "Searches educational materials using optional filters."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Search completed successfully"
    )
    public ResponseEntity<List<EducationalMaterialResource>> searchEducationalMaterials(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(required = false) MaterialCategory category,
            @RequestParam(required = false) MaterialType materialType
    ) {
        var query = new SearchEducationalMaterialsQuery(
                title,
                category,
                materialType
        );
        var materials = educationalMaterialQueryService.handle(query);

        var resources = materials.stream()
                .map(EducationalMaterialResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping("/{materialId}/favorite")
    @Operation(
            summary = "Toggle favorite material",
            description = "Toggles a material as favorite for the current user. Adds if not favorited, removes if already favorited."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Favorite toggled successfully"
            ),
            @ApiResponse(responseCode = "404", description = "Educational material not found")
    })
    public ResponseEntity<?> toggleFavorite(
            @PathVariable Long materialId,
            @RequestParam Long userId
    ) {
        var material = educationalMaterialQueryService.handle(
                new GetEducationalMaterialByIdQuery(materialId)
        );
        if (material.isEmpty()) {
            var error = ApplicationError.notFound("EducationalMaterial", materialId.toString());
            return ErrorResponseAssembler.toErrorResponseFromApplicationError(error);
        }

        var existing = favoriteMaterialPersistenceRepository.findByUserIdAndMaterialId(userId, materialId);
        if (existing.isPresent()) {
            favoriteMaterialPersistenceRepository.delete(existing.get());
            return ResponseEntity.ok().build();
        }

        var favorite = new FavoriteMaterialEntity();
        favorite.setUserId(userId);
        favorite.setMaterialId(materialId);
        favoriteMaterialPersistenceRepository.save(favorite);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{materialId}")
    @Operation(
            summary = "Delete an educational material",
            description = "Deletes the educational material permanently."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Educational material deleted successfully"
            ),
            @ApiResponse(responseCode = "404", description = "Educational material not found")
    })
    public ResponseEntity<?> deleteEducationalMaterial(@PathVariable Long materialId) {
        var result = educationalMaterialCommandService.handle(new DeleteEducationalMaterialCommand(materialId));

        return switch (result) {
            case Result.Success<EducationalMaterial, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();
            case Result.Failure<EducationalMaterial, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
