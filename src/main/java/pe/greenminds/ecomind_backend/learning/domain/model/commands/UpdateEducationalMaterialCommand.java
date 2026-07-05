package pe.greenminds.ecomind_backend.learning.domain.model.commands;

import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;

public record UpdateEducationalMaterialCommand(
        Long id,
        String title,
        String description,
        String content,
        MaterialType materialType,
        MaterialCategory category,
        String imageUrl,
        Integer durationMinutes
) {
}
