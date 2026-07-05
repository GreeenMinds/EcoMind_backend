package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.community.domain.model.events.PostReactionCreatedEvent;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostReaction extends AbstractDomainAggregateRoot<PostReaction> {
    @Getter
    @Setter
    private Long id;

    private Long postId;
    private Long userId;
    private PostReactionType reactionType;
    private LocalDateTime createdAt;

    public PostReaction(Long id, Long postId, Long userId, PostReactionType reactionType, LocalDateTime createdAt) {
        this.id = id;
        this.postId = Objects.requireNonNull(postId, "postId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.reactionType = Objects.requireNonNull(reactionType, "reactionType must not be null");
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public PostReaction(Long postId, Long userId, PostReactionType reactionType) {
        this(null, postId, userId, reactionType, LocalDateTime.now());
    }

    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public PostReactionType getReactionType() { return reactionType; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public PostReaction updateType(PostReactionType reactionType) {
        this.reactionType = Objects.requireNonNull(reactionType, "reactionType must not be null");
        return this;
    }

    public void onCreated() {
        registerDomainEvent(PostReactionCreatedEvent.from(this));
    }
}
