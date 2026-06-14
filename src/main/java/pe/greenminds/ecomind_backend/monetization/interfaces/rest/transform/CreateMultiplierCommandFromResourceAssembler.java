package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateMultiplierResource;

public class CreateMultiplierCommandFromResourceAssembler {

    private CreateMultiplierCommandFromResourceAssembler() {}

    public static CreateMultiplierCommand toCommandFromResource(CreateMultiplierResource resource) {
        return new CreateMultiplierCommand(
                resource.multiplierFactor(),
                resource.durationMinutes(),
                resource.gemCost()
        );
    }
}
