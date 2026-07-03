package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record CreatePostReactionCommand(
        Long postId,
        Long userId
) {
}