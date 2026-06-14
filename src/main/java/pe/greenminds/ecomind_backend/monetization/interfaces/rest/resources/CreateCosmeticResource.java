package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
        name = "CreateCosmeticRequest",
        description = "Request payload for creating a new cosmetic",
        example = """
        {
          "name": "Golden Crown",
          "description": "A shiny golden crown",
          "price": 100,
          "type": "HAT",
          "imageUrl": "https://example.com/crown.png"
        }
        """
)
public record CreateCosmeticResource(
    @NotBlank(message = "is required")
    @Schema(description = "Cosmetic name", example = "Golden Crown")
    String name,

    @Schema(description = "Cosmetic description", example = "A shiny golden crown")
    String description,

    @NotNull
    @PositiveOrZero
    @Schema(description = "Price in gems", example = "100")
    Integer price,

    @NotBlank(message = "is required")
    @Schema(description = "Cosmetic type", example = "HAT")
    String type,

    @Schema(description = "Image URL", example = "https://example.com/crown.png")
    String imageUrl
) {
}
