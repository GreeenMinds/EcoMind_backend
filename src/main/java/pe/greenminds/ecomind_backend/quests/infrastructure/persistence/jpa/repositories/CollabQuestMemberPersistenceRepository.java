package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabMemberStatus;
import pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities.CollabQuestMemberPersistenceEntity;

import java.util.List;

@Repository
public interface CollabQuestMemberPersistenceRepository
        extends JpaRepository<CollabQuestMemberPersistenceEntity, Long> {
    boolean existsBySessionIdAndUserId(Long sessionId, Long userId);

    List<CollabQuestMemberPersistenceEntity> findBySessionIdAndStatusIn(
            Long sessionId,
            List<CollabMemberStatus> statuses
    );

    @Query("""
            select member
            from CollabQuestMemberPersistenceEntity member
            join CollabQuestSessionPersistenceEntity session
                on session.id = member.sessionId
            where member.userId = :userId
                and session.questId = :questId
            """)
    List<CollabQuestMemberPersistenceEntity> findByUserIdAndQuestId(
            @Param("userId") Long userId,
            @Param("questId") Long questId
    );
}
