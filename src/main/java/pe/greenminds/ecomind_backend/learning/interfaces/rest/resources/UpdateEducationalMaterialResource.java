package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "UpdateEducationalMaterialRequest", description = "Complete educational material update payload")
public record UpdateEducationalMaterialResource(
        @Schema(description = "Educational material title", example = "Introduction to Recycling")
        String title,

        @Schema(description = "Educational material description", example = "Learn the basics of recycling and why it matters.")
        String description,

        @Schema(description = "Educational material content", example = "Recycling is the process of converting waste materials into new materials...")
        String content,

        @Schema(description = "Educational material type", example = "TEXT")
        String materialType,

        @Schema(description = "Educational material category", example = "RECYCLE")
        String category,

        @Schema(description = "Image URL for the educational material", example = "https://example.com/recycling.png")
        String imageUrl,

        @Schema(description = "Duration in minutes to complete the material", example = "15")
        Integer durationMinutes
) {
}
