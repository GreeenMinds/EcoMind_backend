package pe.greenminds.ecomind_backend.community.domain.model.queries;

import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;

public record SearchPostReactionsQuery(
        Long postId,
        Long userId,
        PostReactionType reactionType
) {
}
