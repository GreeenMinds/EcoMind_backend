package pe.greenminds.ecomind_backend.community.domain.model.commands;

import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;

public record UpdateCommunityGoalCommand(
        Long id,
        String description,
        Integer target,
        Integer progress,
        Integer participants,
        CommunityGoalStatus status
) {
}
