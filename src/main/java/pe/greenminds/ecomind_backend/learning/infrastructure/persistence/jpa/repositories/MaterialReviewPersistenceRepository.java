package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.MaterialReviewEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaterialReviewPersistenceRepository extends JpaRepository<MaterialReviewEntity, Long> {

    List<MaterialReviewEntity> findByUserId(Long userId);

    Optional<MaterialReviewEntity> findByUserIdAndMaterialId(Long userId, Long materialId);

    boolean existsByUserIdAndMaterialId(Long userId, Long materialId);
}
