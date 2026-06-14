package pe.greenminds.ecomind_backend.monetization.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.commandservices.CosmeticCommandService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.commands.CreateCosmeticCommand;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class CosmeticCommandServiceImpl implements CosmeticCommandService {

    private final CosmeticRepository cosmeticRepository;

    public CosmeticCommandServiceImpl(CosmeticRepository cosmeticRepository) {
        this.cosmeticRepository = cosmeticRepository;
    }

    @Override
    public Result<Cosmetic, ApplicationError> handle(CreateCosmeticCommand command) {
        try {
            var cosmetic = new Cosmetic(
                    command.name(),
                    command.description(),
                    command.price(),
                    command.type(),
                    command.imageUrl()
            );

            return Result.success(cosmeticRepository.save(cosmetic));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("Cosmetic", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("Cosmetic creation", e.getMessage())
            );
        }
    }
}
