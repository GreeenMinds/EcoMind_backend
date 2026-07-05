package pe.greenminds.ecomind_backend.community.application.queryservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.domain.model.queries.CountPostReactionsByPostIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetPostReactionByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchPostReactionsQuery;

import java.util.List;
import java.util.Optional;

public interface PostReactionQueryService {
    List<PostReaction> handle(SearchPostReactionsQuery query);

    Optional<PostReaction> handle(GetPostReactionByIdQuery query);

    long handle(CountPostReactionsByPostIdQuery query);
}
