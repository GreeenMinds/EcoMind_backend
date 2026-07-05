package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record CreateCommunityGoalCommand(
        Long communityId,
        Long goalId,
        String description,
        Integer target
) {
}
