package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.MaterialReviewResource;

public class MaterialReviewResourceFromEntityAssembler {

    private MaterialReviewResourceFromEntityAssembler() {}

    public static MaterialReviewResource toResourceFromEntity(MaterialReview entity) {
        return new MaterialReviewResource(
                entity.getId(),
                entity.getUserId(),
                entity.getMaterialId(),
                entity.getReviewedAt().toString()
        );
    }
}
