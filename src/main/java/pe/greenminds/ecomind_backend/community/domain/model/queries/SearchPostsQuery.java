package pe.greenminds.ecomind_backend.community.domain.model.queries;

import java.time.LocalDateTime;

public record SearchPostsQuery(
        Long communityId,
        Long userId,
        String content
) {
}
