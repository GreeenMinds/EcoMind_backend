package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CosmeticResponse",
        description = "Cosmetic information response",
        example = """
        {
          "id": 1,
          "name": "Golden Crown",
          "description": "A shiny golden crown",
          "price": 100,
          "type": "HAT",
          "imageUrl": "https://example.com/crown.png"
        }
        """
)
public record CosmeticResource(
    @Schema(description = "Cosmetic unique identifier", example = "1")
    Long id,

    @Schema(description = "Cosmetic name", example = "Golden Crown")
    String name,

    @Schema(description = "Cosmetic description", example = "A shiny golden crown")
    String description,

    @Schema(description = "Price in gems", example = "100")
    Integer price,

    @Schema(description = "Cosmetic type", example = "HAT")
    String type,

    @Schema(description = "Image URL", example = "https://example.com/crown.png")
    String imageUrl
) {
}
