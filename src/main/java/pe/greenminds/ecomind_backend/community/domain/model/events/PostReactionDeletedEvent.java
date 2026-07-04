package pe.greenminds.ecomind_backend.community.domain.model.events;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;

import java.time.LocalDateTime;

public record PostReactionDeletedEvent(
        Long postReactionId,
        Long postId,
        Long userId,
        LocalDateTime occurredOn
) {
    public static PostReactionDeletedEvent from(PostReaction postReaction) {
        return new PostReactionDeletedEvent(
                postReaction.getId(),
                postReaction.getPostId(),
                postReaction.getUserId(),
                LocalDateTime.now()
        );
    }
}