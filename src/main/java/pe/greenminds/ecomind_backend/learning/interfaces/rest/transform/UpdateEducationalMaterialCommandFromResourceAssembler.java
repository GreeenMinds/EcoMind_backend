package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.commands.UpdateEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.UpdateEducationalMaterialResource;

public final class UpdateEducationalMaterialCommandFromResourceAssembler {
    private UpdateEducationalMaterialCommandFromResourceAssembler() {
    }

    public static UpdateEducationalMaterialCommand toCommandFromResource(
            Long id,
            UpdateEducationalMaterialResource resource
    ) {
        return new UpdateEducationalMaterialCommand(
                id,
                resource.title(),
                resource.description(),
                resource.content(),
                resource.materialType() != null ? MaterialType.valueOf(resource.materialType()) : null,
                resource.category() != null ? MaterialCategory.valueOf(resource.category()) : null,
                resource.imageUrl(),
                resource.durationMinutes()
        );
    }
}
