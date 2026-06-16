package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

@Schema(
        name = "CreateMultiplierRequest",
        description = "Request payload for creating a new multiplier",
        example = """
        {
          "multiplierFactor": 2.0,
          "durationMinutes": 30,
          "gemCost": 50
        }
        """
)
public record CreateMultiplierResource(
    @NotNull
    @PositiveOrZero
    @Schema(description = "Multiplier factor", example = "2.0")
    BigDecimal multiplierFactor,

    @NotNull
    @PositiveOrZero
    @Schema(description = "Duration in minutes", example = "30")
    Integer durationMinutes,

    @NotNull
    @PositiveOrZero
    @Schema(description = "Cost in gems", example = "50")
    Integer gemCost
) {
}
