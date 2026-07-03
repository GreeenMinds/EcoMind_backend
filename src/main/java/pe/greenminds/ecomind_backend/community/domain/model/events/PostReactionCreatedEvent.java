package pe.greenminds.ecomind_backend.community.domain.model.events;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;

import java.time.LocalDateTime;

public record PostReactionCreatedEvent(
        Long postReactionId,
        Long postId,
        Long userId,
        LocalDateTime createdAt,
        LocalDateTime occurredOn
) {
    public static PostReactionCreatedEvent from(PostReaction postReaction) {
        return new PostReactionCreatedEvent(
                postReaction.getId(),
                postReaction.getPostId(),
                postReaction.getUserId(),
                postReaction.getCreatedAt(),
                LocalDateTime.now()
        );
    }
}