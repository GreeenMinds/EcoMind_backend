package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.EducationalMaterialEntity;

import java.util.List;

@Repository
public interface EducationalMaterialPersistenceRepository extends JpaRepository<EducationalMaterialEntity, Long> {

    @Query("""
    SELECT e FROM EducationalMaterialEntity e
    WHERE (:title = '' OR LOWER(e.title) LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:category IS NULL OR e.category = :category)
      AND (:materialType IS NULL OR e.materialType = :materialType)
    """)
    List<EducationalMaterialEntity> search(
            @Param("title") String title,
            @Param("category") MaterialCategory category,
            @Param("materialType") MaterialType materialType
    );
}
