package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.commands.CreatePostReactionCommand;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreatePostReactionResource;

public class CreatePostReactionCommandFromResourceAssembler {
    private CreatePostReactionCommandFromResourceAssembler() {}

    public static CreatePostReactionCommand toCommandFromResource(CreatePostReactionResource resource) {
        return new CreatePostReactionCommand(
                resource.postId(),
                resource.userId(),
                PostReactionType.fromValue(resource.reactionType())
        );
    }
}
