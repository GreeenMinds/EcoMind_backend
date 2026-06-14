package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(
        name = "MultiplierResponse",
        description = "Multiplier information response",
        example = """
        {
          "id": 1,
          "multiplierFactor": 2.0,
          "durationMinutes": 30,
          "gemCost": 50
        }
        """
)
public record MultiplierResource(
    @Schema(description = "Multiplier unique identifier", example = "1")
    Long id,

    @Schema(description = "Multiplier factor", example = "2.0")
    BigDecimal multiplierFactor,

    @Schema(description = "Duration in minutes", example = "30")
    Integer durationMinutes,

    @Schema(description = "Cost in gems", example = "50")
    Integer gemCost
) {
}
