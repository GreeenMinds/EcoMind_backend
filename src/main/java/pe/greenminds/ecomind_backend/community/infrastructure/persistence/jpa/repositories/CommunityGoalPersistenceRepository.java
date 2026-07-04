package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.CommunityGoalPersistenceEntity;

import java.util.List;

@Repository
public interface CommunityGoalPersistenceRepository extends JpaRepository<CommunityGoalPersistenceEntity, Long> {

    @Query("""
        SELECT cg FROM CommunityGoalPersistenceEntity cg
        WHERE (:communityId IS NULL OR cg.communityId = :communityId)
    """)
    List<CommunityGoalPersistenceEntity> search(
            @Param("communityId") Long communityId
    );

    boolean existsByCommunityIdAndGoalId(Long communityId, Long goalId);
}
