package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.MaterialReviewEntity;

public class MaterialReviewPersistenceAssembler {
    private MaterialReviewPersistenceAssembler() {}

    public static MaterialReview toDomainFromPersistence(MaterialReviewEntity entity) {
        var review = new MaterialReview(
                entity.getUserId(),
                entity.getMaterialId(),
                entity.getReviewedAt()
        );
        review.setId(entity.getId());
        return review;
    }

    public static MaterialReviewEntity toPersistenceFromDomain(MaterialReview review) {
        var entity = new MaterialReviewEntity();
        entity.setId(review.getId());
        entity.setUserId(review.getUserId());
        entity.setMaterialId(review.getMaterialId());
        entity.setReviewedAt(review.getReviewedAt());
        return entity;
    }
}
