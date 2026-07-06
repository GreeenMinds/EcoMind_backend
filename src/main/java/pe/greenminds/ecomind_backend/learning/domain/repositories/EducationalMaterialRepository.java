package pe.greenminds.ecomind_backend.learning.domain.repositories;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;

import java.util.List;
import java.util.Optional;

public interface EducationalMaterialRepository {
    Optional<EducationalMaterial> findById(Long id);
    List<EducationalMaterial> findAll();
    List<EducationalMaterial> search(String title, MaterialCategory category, MaterialType materialType);
    EducationalMaterial save(EducationalMaterial educationalMaterial);
    void deleteById(Long id);
    boolean existsById(Long id);
}
