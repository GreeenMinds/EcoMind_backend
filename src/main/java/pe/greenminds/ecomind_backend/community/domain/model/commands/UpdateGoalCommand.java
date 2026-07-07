package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record UpdateGoalCommand(
        Long id,
        String title,
        String unit,
        String questCategory
) {
}