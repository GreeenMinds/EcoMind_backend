package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record CreateGoalCommand(
        String title,
        String unit,
        String questCategory
) {
}