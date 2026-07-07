package pe.greenminds.ecomind_backend.learning.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.learning.application.commandservices.MaterialReviewCommandService;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;
import pe.greenminds.ecomind_backend.learning.domain.model.commands.MarkMaterialAsReviewedCommand;
import pe.greenminds.ecomind_backend.learning.domain.repositories.MaterialReviewRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.time.LocalDateTime;

@Service
public class MaterialReviewCommandServiceImpl implements MaterialReviewCommandService {

    private final MaterialReviewRepository materialReviewRepository;

    public MaterialReviewCommandServiceImpl(MaterialReviewRepository materialReviewRepository) {
        this.materialReviewRepository = materialReviewRepository;
    }

    @Override
    public Result<MaterialReview, ApplicationError> handle(MarkMaterialAsReviewedCommand command) {
        try {
            var materialReview = new MaterialReview(
                    command.userId(),
                    command.materialId(),
                    LocalDateTime.now()
            );
            return Result.success(materialReviewRepository.save(materialReview));
        } catch (IllegalArgumentException e) {
            return Result.failure(
                    ApplicationError.validationError("MaterialReview", e.getMessage())
            );
        } catch (Exception e) {
            return Result.failure(
                    ApplicationError.unexpected("MaterialReview creation", e.getMessage())
            );
        }
    }
}
