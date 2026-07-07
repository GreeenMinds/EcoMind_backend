package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.commands.CreateEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.CreateEducationalMaterialResource;

public class CreateEducationalMaterialCommandFromResourceAssembler {

    private CreateEducationalMaterialCommandFromResourceAssembler() {}

    public static CreateEducationalMaterialCommand toCommandFromResource(
            CreateEducationalMaterialResource resource
    ) {
        return new CreateEducationalMaterialCommand(
                resource.title(),
                resource.description(),
                resource.content(),
                MaterialType.valueOf(resource.materialType()),
                MaterialCategory.valueOf(resource.category()),
                resource.imageUrl(),
                resource.durationMinutes()
        );
    }
}
