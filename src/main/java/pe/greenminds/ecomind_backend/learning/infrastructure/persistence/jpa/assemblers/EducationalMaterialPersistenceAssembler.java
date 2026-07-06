package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.EducationalMaterialEntity;

public class EducationalMaterialPersistenceAssembler {
    private EducationalMaterialPersistenceAssembler() {}

    public static EducationalMaterial toDomainFromPersistence(EducationalMaterialEntity entity) {
        var material = new EducationalMaterial(
                entity.getTitle(),
                entity.getDescription(),
                entity.getContent(),
                entity.getMaterialType(),
                entity.getCategory(),
                entity.getImageUrl(),
                entity.getDurationMinutes(),
                entity.getLanguage()
        );
        material.setId(entity.getId());
        return material;
    }

    public static EducationalMaterialEntity toPersistenceFromDomain(EducationalMaterial material) {
        var entity = new EducationalMaterialEntity();
        entity.setId(material.getId());
        entity.setTitle(material.getTitle());
        entity.setDescription(material.getDescription());
        entity.setContent(material.getContent());
        entity.setMaterialType(material.getMaterialType());
        entity.setCategory(material.getCategory());
        entity.setImageUrl(material.getImageUrl());
        entity.setDurationMinutes(material.getDurationMinutes());
        entity.setLanguage(material.getLanguage());
        return entity;
    }
}
