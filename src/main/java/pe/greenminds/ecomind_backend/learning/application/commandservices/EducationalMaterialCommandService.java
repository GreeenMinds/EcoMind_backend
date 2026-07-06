package pe.greenminds.ecomind_backend.learning.application.commandservices;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CreateEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.DeleteEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.UpdateEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface EducationalMaterialCommandService {

    Result<EducationalMaterial, ApplicationError> handle(CreateEducationalMaterialCommand command);

    Result<EducationalMaterial, ApplicationError> handle(UpdateEducationalMaterialCommand command);

    Result<EducationalMaterial, ApplicationError> handle(DeleteEducationalMaterialCommand command);
}
