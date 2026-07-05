package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.IncrementCommunityGoalProgressCommand;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.IncrementCommunityGoalProgressResource;

public class IncrementCommunityGoalProgressCommandFromResourceAssembler {

    private IncrementCommunityGoalProgressCommandFromResourceAssembler() {}

    public static IncrementCommunityGoalProgressCommand toCommandFromResource(Long id, IncrementCommunityGoalProgressResource resource) {
        return new IncrementCommunityGoalProgressCommand(
                id,
                resource.increment()
        );
    }
}
