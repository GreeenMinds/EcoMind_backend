package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.MultiplierCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.MultiplierRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class MultiplierCommandServiceImpl implements MultiplierCommandService {

    private final MultiplierRepository multiplierRepository;

    public MultiplierCommandServiceImpl(MultiplierRepository multiplierRepository) {
        this.multiplierRepository = multiplierRepository;
    }

    @Override
    public Result<Multiplier, ApplicationError> handle(CreateMultiplierCommand command) {
        try {
            var multiplier = new Multiplier(
                    command.multiplierFactor(),
                    command.durationMinutes(),
                    command.gemCost()
            );

            return Result.success(multiplierRepository.save(multiplier));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("Multiplier", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("Multiplier creation", e.getMessage())
            );
        }
    }
}
