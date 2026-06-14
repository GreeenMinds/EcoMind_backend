package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Optional<Post> findById(Long id);

    List<Post> search(
            Long communityId,
            Long userId,
            String content
    );

    Post save(Post post);

    void deleteById(Long id);

    boolean existsById(Long id);
}
