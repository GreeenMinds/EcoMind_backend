package pe.greenminds.ecomind_backend.learning.domain.model.events;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;

public record EducationalMaterialCreatedEvent(
        Long materialId,
        String title,
        String description,
        MaterialType materialType,
        MaterialCategory category
) {
    public static EducationalMaterialCreatedEvent from(EducationalMaterial material) {
        return new EducationalMaterialCreatedEvent(
                material.getId(),
                material.getTitle(),
                material.getDescription(),
                material.getMaterialType(),
                material.getCategory()
        );
    }
}
