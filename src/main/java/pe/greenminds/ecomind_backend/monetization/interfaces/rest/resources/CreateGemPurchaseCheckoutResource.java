package pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreateGemPurchaseCheckoutRequest",
        description = "Request payload for starting a gem package checkout. The price and payment status are decided by the server, never by the client.",
        example = """
        {
          "userId": 10,
          "packageId": 2
        }
        """
)
public record CreateGemPurchaseCheckoutResource(
    @NotNull
    @Schema(description = "User ID who wants to buy the package", example = "10")
    Long userId,

    @NotNull
    @Schema(description = "Gem package ID to purchase", example = "2")
    Long packageId
) {
}
