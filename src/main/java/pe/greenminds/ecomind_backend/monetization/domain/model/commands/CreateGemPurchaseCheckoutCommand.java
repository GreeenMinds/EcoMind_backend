package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

public record CreateGemPurchaseCheckoutCommand(
        Long userId,
        Long packageId
) {
}
