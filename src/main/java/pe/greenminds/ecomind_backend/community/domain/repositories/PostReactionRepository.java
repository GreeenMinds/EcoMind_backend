package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;

import java.util.List;
import java.util.Optional;

public interface PostReactionRepository {
    Optional<PostReaction> findById(Long id);

    Optional<PostReaction> findByPostIdAndUserId(Long postId, Long userId);

    List<PostReaction> search(Long postId, Long userId);

    PostReaction save(PostReaction postReaction);

    void deleteByPostIdAndUserId(Long postId, Long userId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    long countByPostId(Long postId);
}