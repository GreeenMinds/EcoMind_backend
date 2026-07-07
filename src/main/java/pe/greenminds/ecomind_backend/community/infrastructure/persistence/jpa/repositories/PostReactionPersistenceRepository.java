package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.PostReactionPersistenceEntity;

import java.util.List;

@Repository
public interface PostReactionPersistenceRepository extends JpaRepository<PostReactionPersistenceEntity, Long> {

    @Query("""
        SELECT pr FROM PostReactionPersistenceEntity pr
        WHERE (:postId IS NULL OR pr.postId = :postId)
          AND (:userId IS NULL OR pr.userId = :userId)
          AND (:reactionType IS NULL OR pr.reactionType = :reactionType)
    """)
    List<PostReactionPersistenceEntity> search(
            @Param("postId") Long postId,
            @Param("userId") Long userId,
            @Param("reactionType") PostReactionType reactionType
    );

    boolean existsByPostIdAndUserId(Long postId, Long userId);

    long countByPostId(Long postId);
}
