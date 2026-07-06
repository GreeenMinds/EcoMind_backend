package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "MaterialReviewResponse",
        description = "Material review information response",
        example = """
                {
                  "id": 1,
                  "userId": 1,
                  "materialId": 1,
                  "reviewedAt": "2026-07-05T10:30:00"
                }
                """
)
public record MaterialReviewResource(
        @Schema(description = "Review unique identifier", example = "1")
        Long id,

        @Schema(description = "User who reviewed the material", example = "1")
        Long userId,

        @Schema(description = "Reviewed material identifier", example = "1")
        Long materialId,

        @Schema(description = "Timestamp when the material was reviewed", example = "2026-07-05T10:30:00")
        String reviewedAt
) {
}
