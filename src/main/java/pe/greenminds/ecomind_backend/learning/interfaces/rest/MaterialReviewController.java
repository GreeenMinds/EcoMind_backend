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
import pe.greenminds.ecomind_backend.learning.application.commandservices.MaterialReviewCommandService;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.MarkAsReviewedResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.MaterialReviewResource;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.MarkAsReviewedCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.transform.MaterialReviewResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

@RestController
@RequestMapping(value = "/api/v1/material-reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Material Reviews", description = "Material review management endpoints")
public class MaterialReviewController {
    private final MaterialReviewCommandService materialReviewCommandService;

    public MaterialReviewController(MaterialReviewCommandService materialReviewCommandService) {
        this.materialReviewCommandService = materialReviewCommandService;
    }

    @PostMapping
    @Operation(
            summary = "Mark a material as reviewed",
            description = "Creates a review record for a material by a user."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Material marked as reviewed successfully",
                    content = @Content(schema = @Schema(implementation = MaterialReviewResource.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> markAsReviewed(@Valid @RequestBody MarkAsReviewedResource resource) {
        var command = MarkAsReviewedCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = materialReviewCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                MaterialReviewResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }
}
