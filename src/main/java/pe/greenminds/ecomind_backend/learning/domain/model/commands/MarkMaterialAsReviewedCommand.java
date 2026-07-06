package pe.greenminds.ecomind_backend.learning.domain.model.commands;

public record MarkMaterialAsReviewedCommand(
        Long userId,
        Long materialId
) {
}
