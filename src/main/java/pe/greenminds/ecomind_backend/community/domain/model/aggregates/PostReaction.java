package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.community.domain.model.events.PostReactionCreatedEvent;
import pe.greenminds.ecomind_backend.community.domain.model.events.PostReactionDeletedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class PostReaction extends AbstractDomainAggregateRoot<PostReaction> {
    @Getter
    @Setter
    private Long id;

    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;

    public PostReaction(Long id, Long postId, Long userId, LocalDateTime createdAt) {
        this.id = id;
        this.postId = Objects.requireNonNull(postId, "postId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public PostReaction(Long postId, Long userId) {
        this(null, postId, userId, LocalDateTime.now());
    }

    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void onCreated() {
        registerDomainEvent(PostReactionCreatedEvent.from(this));
    }

    public void onDeleted() {
        registerDomainEvent(PostReactionDeletedEvent.from(this));
    }
}