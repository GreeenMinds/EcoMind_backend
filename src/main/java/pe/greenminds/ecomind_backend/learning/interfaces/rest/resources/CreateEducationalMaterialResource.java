package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateEducationalMaterialRequest",
        description = "Request payload for creating a new educational material",
        example = """
                {
                  "title": "Introduction to Recycling",
                  "description": "Learn the basics of recycling and why it matters.",
                  "content": "Recycling is the process of converting waste materials into new materials...",
                  "materialType": "TEXT",
                  "category": "RECYCLE",
                  "imageUrl": "https://example.com/recycling.png",
                  "durationMinutes": 15
                }
                """
)
public record CreateEducationalMaterialResource(
        @NotBlank(message = "is required")
        @Schema(description = "Educational material title", example = "Introduction to Recycling", minLength = 1, maxLength = 100)
        String title,

        @Schema(description = "Educational material description", example = "Learn the basics of recycling and why it matters.")
        String description,

        @NotBlank(message = "is required")
        @Schema(description = "Educational material content", example = "Recycling is the process of converting waste materials into new materials...")
        String content,

        @NotNull(message = "is required")
        @Schema(description = "Educational material type", example = "TEXT")
        String materialType,

        @NotNull(message = "is required")
        @Schema(description = "Educational material category", example = "RECYCLE")
        String category,

        @Schema(description = "Image URL for the educational material", example = "https://example.com/recycling.png")
        String imageUrl,

        @Schema(description = "Duration in minutes to complete the material", example = "15")
        Integer durationMinutes
) {
}
