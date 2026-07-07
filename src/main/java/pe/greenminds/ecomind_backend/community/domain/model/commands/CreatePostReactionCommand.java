package pe.greenminds.ecomind_backend.community.domain.model.commands;

import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;

public record CreatePostReactionCommand(
        Long postId,
        Long userId,
        PostReactionType reactionType
) {
}
