package pe.greenminds.ecomind_backend.learning.domain.model.events;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;

public record MaterialReviewedEvent(
        Long userId,
        Long materialId
) {
    public static MaterialReviewedEvent from(MaterialReview review) {
        return new MaterialReviewedEvent(
                review.getUserId(),
                review.getMaterialId()
        );
    }
}
