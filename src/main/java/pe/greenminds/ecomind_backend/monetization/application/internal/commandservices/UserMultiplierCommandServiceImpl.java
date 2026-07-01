package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserMultiplierCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.MultiplierRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserMultiplierRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class UserMultiplierCommandServiceImpl implements UserMultiplierCommandService {

    private final UserMultiplierRepository userMultiplierRepository;
    private final MultiplierRepository multiplierRepository;

    public UserMultiplierCommandServiceImpl(UserMultiplierRepository userMultiplierRepository, MultiplierRepository multiplierRepository) {
        this.userMultiplierRepository = userMultiplierRepository;
        this.multiplierRepository = multiplierRepository;
    }

    @Transactional
    @Override
    public Result<UserMultiplier, ApplicationError> handle(CreateUserMultiplierCommand command) {
        if (!multiplierRepository.existsById(command.multiplierId())) {
            return Result.failure(
                    ApplicationError.notFound("Multiplier", command.multiplierId().toString())
            );
        }

        try {
            var userMultiplier = new UserMultiplier(
                    command.userId(),
                    command.multiplierId(),
                    command.startDate(),
                    command.endDate()
            );

            return Result.success(userMultiplierRepository.save(userMultiplier));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("UserMultiplier", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("UserMultiplier creation", e.getMessage())
            );
        }
    }
}
