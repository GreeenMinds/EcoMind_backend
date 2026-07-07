package pe.greenminds.ecomind_backend.learning.domain.model.queries;

import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;

public record SearchEducationalMaterialsQuery(
        String title,
        MaterialCategory category,
        MaterialType materialType
) {
}
