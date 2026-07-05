package pe.greenminds.ecomind_backend.community.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.PostReactionResource;

public class PostReactionResourceFromEntityAssembler {
    private PostReactionResourceFromEntityAssembler() {}

    public static PostReactionResource toResourceFromEntity(PostReaction postReaction) {
        return new PostReactionResource(
                postReaction.getId(),
                postReaction.getPostId(),
                postReaction.getUserId(),
                postReaction.getReactionType().getValue(),
                postReaction.getCreatedAt()
        );
    }
}
