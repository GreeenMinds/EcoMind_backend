package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.EducationalMaterialResource;

import java.util.List;

public class EducationalMaterialResourceFromEntityAssembler {

    private EducationalMaterialResourceFromEntityAssembler() {}

    public static EducationalMaterialResource toResourceFromEntity(EducationalMaterial entity) {
        return new EducationalMaterialResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getContent(),
                entity.getMaterialType().name(),
                entity.getCategory().name(),
                entity.getImageUrl(),
                entity.getVideoUrl(),
                entity.getDurationMinutes(),
                entity.getLanguage()
        );
    }

    public static List<EducationalMaterialResource> toResourceFromEntityList(List<EducationalMaterial> entities) {
        return entities.stream()
                .map(EducationalMaterialResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
    }
}
