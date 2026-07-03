package pe.greenminds.ecomind_backend.community.domain.model.queries;

public record SearchPostReactionsQuery(
        Long postId,
        Long userId
) {
}