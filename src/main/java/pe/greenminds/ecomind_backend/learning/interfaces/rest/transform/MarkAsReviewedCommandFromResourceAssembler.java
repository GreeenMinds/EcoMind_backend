package pe.greenminds.ecomind_backend.learning.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.learning.domain.model.commands.MarkMaterialAsReviewedCommand;
import pe.greenminds.ecomind_backend.learning.interfaces.rest.resources.MarkAsReviewedResource;

public class MarkAsReviewedCommandFromResourceAssembler {

    private MarkAsReviewedCommandFromResourceAssembler() {}

    public static MarkMaterialAsReviewedCommand toCommandFromResource(
            MarkAsReviewedResource resource
    ) {
        return new MarkMaterialAsReviewedCommand(
                resource.userId(),
                resource.materialId()
        );
    }
}
