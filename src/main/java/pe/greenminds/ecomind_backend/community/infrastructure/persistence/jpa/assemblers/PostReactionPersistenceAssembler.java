package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.PostReactionPersistenceEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class PostReactionPersistenceAssembler {
    private PostReactionPersistenceAssembler() {}

    public static PostReaction toDomainFromPersistence(PostReactionPersistenceEntity entity) {
        LocalDateTime createdAt = entity.getCreatedAt() == null
                ? null
                : entity.getCreatedAt().toInstant()
                  .atZone(ZoneId.systemDefault())
                  .toLocalDateTime();

        return new PostReaction(
                entity.getId(),
                entity.getPostId(),
                entity.getUserId(),
                entity.getReactionType(),
                createdAt
        );
    }

    public static PostReactionPersistenceEntity toPersistenceFromDomain(PostReaction postReaction) {
        var entity = new PostReactionPersistenceEntity();

        entity.setId(postReaction.getId());
        entity.setPostId(postReaction.getPostId());
        entity.setUserId(postReaction.getUserId());
        entity.setReactionType(postReaction.getReactionType());

        return entity;
    }
}
