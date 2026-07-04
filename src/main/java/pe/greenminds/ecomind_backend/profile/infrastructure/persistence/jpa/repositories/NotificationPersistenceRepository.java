package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;

import java.util.List;

@Repository
public interface NotificationPersistenceRepository extends JpaRepository<NotificationPersistenceEntity, Long> {
    @Query("""
            SELECT n FROM NotificationPersistenceEntity n
            WHERE (:userId IS NULL OR n.userId = :userId)
              AND (:read IS NULL OR n.read = :read)
            ORDER BY n.createdAt DESC
            """)
    List<NotificationPersistenceEntity> search(
            @Param("userId") Long userId,
            @Param("read") Boolean read
    );

    long countByUserIdAndReadFalse(Long userId);
}
