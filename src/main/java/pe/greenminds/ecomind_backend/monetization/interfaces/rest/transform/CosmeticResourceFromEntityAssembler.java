package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CosmeticResource;

public class CosmeticResourceFromEntityAssembler {

    private CosmeticResourceFromEntityAssembler() {}

    public static CosmeticResource toResourceFromEntity(Cosmetic cosmetic) {
        return new CosmeticResource(
                cosmetic.getId(),
                cosmetic.getName(),
                cosmetic.getDescription(),
                cosmetic.getPrice(),
                cosmetic.getType().name().toLowerCase(),
                cosmetic.getImageUrl()
        );
    }
}
