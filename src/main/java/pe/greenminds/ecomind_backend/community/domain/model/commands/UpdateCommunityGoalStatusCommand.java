package pe.greenminds.ecomind_backend.community.domain.model.commands;

import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;

public record UpdateCommunityGoalStatusCommand(
        Long id,
        CommunityGoalStatus status
) {
}
