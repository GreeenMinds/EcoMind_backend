package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateGemPurchaseCheckoutCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateGemPurchaseCheckoutResource;

public class CreateGemPurchaseCheckoutCommandFromResourceAssembler {

    private CreateGemPurchaseCheckoutCommandFromResourceAssembler() {}

    public static CreateGemPurchaseCheckoutCommand toCommandFromResource(CreateGemPurchaseCheckoutResource resource) {
        return new CreateGemPurchaseCheckoutCommand(
                resource.userId(),
                resource.packageId()
        );
    }
}
