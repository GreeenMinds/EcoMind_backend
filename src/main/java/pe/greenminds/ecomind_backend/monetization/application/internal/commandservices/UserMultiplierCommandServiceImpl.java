package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.UserMultiplierCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserMultiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateUserMultiplierCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserMultiplierRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class UserMultiplierCommandServiceImpl implements UserMultiplierCommandService {

    private final UserMultiplierRepository userMultiplierRepository;

    public UserMultiplierCommandServiceImpl(UserMultiplierRepository userMultiplierRepository) {
        this.userMultiplierRepository = userMultiplierRepository;
    }

    @Override
    public Result<UserMultiplier, ApplicationError> handle(CreateUserMultiplierCommand command) {
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
