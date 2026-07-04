package pe.greenminds.ecomind_backend.community.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.queryservices.PostReactionQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.domain.model.queries.CountPostReactionsByPostIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetPostReactionByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchPostReactionsQuery;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostReactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostReactionQueryServiceImpl implements PostReactionQueryService {

    private final PostReactionRepository postReactionRepository;

    public PostReactionQueryServiceImpl(PostReactionRepository postReactionRepository) {
        this.postReactionRepository = postReactionRepository;
    }

    @Override
    public List<PostReaction> handle(SearchPostReactionsQuery query) {
        return postReactionRepository.search(
                query.postId(),
                query.userId(),
                query.reactionType()
        );
    }

    @Override
    public Optional<PostReaction> handle(GetPostReactionByIdQuery query) {
        return postReactionRepository.findById(query.id());
    }

    @Override
    public long handle(CountPostReactionsByPostIdQuery query) {
        return postReactionRepository.countByPostId(query.postId());
    }
}
