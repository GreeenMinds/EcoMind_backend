package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdatePostReactionTypeCommand;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdatePostReactionTypeResource;

public class UpdatePostReactionTypeCommandFromResourceAssembler {
    private UpdatePostReactionTypeCommandFromResourceAssembler() {}

    public static UpdatePostReactionTypeCommand toCommandFromResource(Long id, UpdatePostReactionTypeResource resource) {
        return new UpdatePostReactionTypeCommand(
                id,
                PostReactionType.fromValue(resource.reactionType())
        );
    }
}
