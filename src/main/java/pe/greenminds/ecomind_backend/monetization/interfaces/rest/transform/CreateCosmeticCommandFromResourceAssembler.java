package pe.greenminds.ecomind_backend.monetization.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.CosmeticType;
import pe.greenminds.ecomind_backend.monetization.interfaces.rest.resources.CreateCosmeticResource;

public class CreateCosmeticCommandFromResourceAssembler {

    private CreateCosmeticCommandFromResourceAssembler() {}

    public static CreateCosmeticCommand toCommandFromResource(CreateCosmeticResource resource) {
        return new CreateCosmeticCommand(
                resource.name(),
                resource.description(),
                resource.price(),
                CosmeticType.valueOf(resource.type().toUpperCase()),
                resource.imageUrl()
        );
    }
}
