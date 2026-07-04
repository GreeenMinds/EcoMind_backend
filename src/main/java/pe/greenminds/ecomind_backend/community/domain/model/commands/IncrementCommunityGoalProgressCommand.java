package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record IncrementCommunityGoalProgressCommand(
        Long id,
        Integer increment
) {
}
