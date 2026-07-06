package pe.greenminds.ecomind_backend.learning.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.learning.application.commandservices.EducationalMaterialCommandService;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.CreateEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.DeleteEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.UpdateEducationalMaterialCommand;
import pe.greenminds.ecomind_backend.learning.domain.repositories.EducationalMaterialRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class EducationalMaterialCommandServiceImpl implements EducationalMaterialCommandService {

    private final EducationalMaterialRepository educationalMaterialRepository;

    public EducationalMaterialCommandServiceImpl(EducationalMaterialRepository educationalMaterialRepository) {
        this.educationalMaterialRepository = educationalMaterialRepository;
    }

    @Override
    public Result<EducationalMaterial, ApplicationError> handle(CreateEducationalMaterialCommand command) {
        try {
            var educationalMaterial = new EducationalMaterial(
                    command.title(),
                    command.description(),
                    command.content(),
                    command.materialType(),
                    command.category(),
                    command.imageUrl(),
                    null,
                    command.durationMinutes(),
                    null
            );
            return Result.success(educationalMaterialRepository.save(educationalMaterial));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("EducationalMaterial", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("EducationalMaterial creation", e.getMessage())
            );
        }
    }

    @Override
    public Result<EducationalMaterial, ApplicationError> handle(UpdateEducationalMaterialCommand command) {
        try {
            var existing = educationalMaterialRepository.findById(command.id());
            if (existing.isEmpty()) {
                return Result.failure(
                        ApplicationError.notFound("EducationalMaterial", command.id().toString())
                );
            }
            var updated = new EducationalMaterial(
                    command.id(),
                    command.title(),
                    command.description(),
                    command.content(),
                    command.materialType(),
                    command.category(),
                    command.imageUrl(),
                    null,
                    command.durationMinutes(),
                    existing.get().getLanguage()
            );
            return Result.success(educationalMaterialRepository.save(updated));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("EducationalMaterial", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("EducationalMaterial update", e.getMessage())
            );
        }
    }

    @Override
    public Result<EducationalMaterial, ApplicationError> handle(DeleteEducationalMaterialCommand command) {
        try {
            var existing = educationalMaterialRepository.findById(command.materialId());
            if (existing.isEmpty()) {
                return Result.failure(
                        ApplicationError.notFound("EducationalMaterial", command.materialId().toString())
                );
            }
            educationalMaterialRepository.deleteById(command.materialId());
            return Result.success(existing.get());
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("EducationalMaterial deletion", e.getMessage())
            );
        }
    }
}
