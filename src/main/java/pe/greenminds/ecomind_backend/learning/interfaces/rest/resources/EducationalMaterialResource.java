package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "EducationalMaterialResponse",
        description = "Educational material information response",
        example = """
                {
                  "id": 1,
                  "title": "Introduction to Recycling",
                  "description": "Learn the basics of recycling and why it matters.",
                  "content": "Recycling is the process of converting waste materials into new materials...",
                  "materialType": "TEXT",
                  "category": "RECYCLE",
                  "imageUrl": "https://example.com/recycling.png",
                  "durationMinutes": 15,
                  "language": "en"
                }
                """
)
public record EducationalMaterialResource(
        @Schema(description = "Educational material unique identifier", example = "1")
        Long id,

        @Schema(description = "Educational material title", example = "Introduction to Recycling", minLength = 1, maxLength = 100)
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

        @Schema(description = "Video URL for video-type materials (YouTube embed)", example = "https://www.youtube.com/embed/xxxx")
        String videoUrl,

        @Schema(description = "Duration in minutes to complete the material", example = "15")
        Integer durationMinutes,

        @Schema(description = "Material language", example = "en")
        String language
) {
}
