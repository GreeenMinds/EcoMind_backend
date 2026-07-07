package pe.greenminds.ecomind_backend.learning.domain.repositories;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;

import java.util.List;
import java.util.Optional;

public interface MaterialReviewRepository {
    Optional<MaterialReview> findById(Long id);
    List<MaterialReview> findByUserId(Long userId);
    Optional<MaterialReview> findByUserIdAndMaterialId(Long userId, Long materialId);
    MaterialReview save(MaterialReview materialReview);
    boolean existsByUserIdAndMaterialId(Long userId, Long materialId);
}
