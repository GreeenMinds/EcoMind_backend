package pe.greenminds.ecomind_backend.monetization.application.commandservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateMultiplierCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface MultiplierCommandService {

    Result<Multiplier, ApplicationError> handle(CreateMultiplierCommand command);
}
