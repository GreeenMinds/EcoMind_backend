package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.MultiplierResource;

public class MultiplierResourceFromEntityAssembler {

    private MultiplierResourceFromEntityAssembler() {}

    public static MultiplierResource toResourceFromEntity(Multiplier multiplier) {
        return new MultiplierResource(
                multiplier.getId(),
                multiplier.getMultiplierFactor(),
                multiplier.getDurationMinutes(),
                multiplier.getGemCost()
        );
    }
}
