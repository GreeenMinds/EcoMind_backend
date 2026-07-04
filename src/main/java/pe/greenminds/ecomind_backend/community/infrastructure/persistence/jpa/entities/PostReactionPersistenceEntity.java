package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(
        name = "community_post_reactions",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"post_id", "user_id"})
        }
)
public class PostReactionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "reaction_type", nullable = false)
    private PostReactionType reactionType;

    public Long getPostId() { return postId; }
    public Long getUserId() { return userId; }
    public PostReactionType getReactionType() { return reactionType; }

    public void setPostId(Long postId) { this.postId = postId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setReactionType(PostReactionType reactionType) { this.reactionType = reactionType; }
}
