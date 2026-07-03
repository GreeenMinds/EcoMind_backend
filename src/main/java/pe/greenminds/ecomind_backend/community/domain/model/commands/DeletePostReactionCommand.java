package pe.greenminds.ecomind_backend.community.domain.model.commands;

public record DeletePostReactionCommand(
        Long postId,
        Long userId
) {
}