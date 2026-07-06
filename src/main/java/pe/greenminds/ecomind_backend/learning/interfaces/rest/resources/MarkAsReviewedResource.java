package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "MarkAsReviewedRequest",
        description = "Request payload for marking a material as reviewed",
        example = """
                {
                  "userId": 1,
                  "materialId": 1
                }
                """
)
public record MarkAsReviewedResource(
        @Schema(description = "User identifier", example = "1")
        Long userId,

        @Schema(description = "Material identifier to mark as reviewed", example = "1")
        Long materialId
) {
}
