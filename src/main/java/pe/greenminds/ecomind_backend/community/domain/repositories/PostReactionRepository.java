package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;

import java.util.List;
import java.util.Optional;

public interface PostReactionRepository {
    Optional<PostReaction> findById(Long id);

    List<PostReaction> search(Long postId, Long userId, PostReactionType reactionType);

    PostReaction save(PostReaction postReaction);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    long countByPostId(Long postId);
}
