package pe.greenminds.ecomind_backend.learning.application.commandservices;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.MarkMaterialAsReviewedCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface MaterialReviewCommandService {

    Result<MaterialReview, ApplicationError> handle(MarkMaterialAsReviewedCommand command);
}
