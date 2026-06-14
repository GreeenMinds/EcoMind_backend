package pe.greenminds.ecomind_backend.monetization.application.commandservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateCosmeticCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface CosmeticCommandService {

    Result<Cosmetic, ApplicationError> handle(CreateCosmeticCommand command);
}
