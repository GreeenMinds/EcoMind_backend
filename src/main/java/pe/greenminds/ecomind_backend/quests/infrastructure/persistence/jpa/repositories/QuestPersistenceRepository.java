package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities.QuestPersistenceEntity;

import java.util.Optional;

@Repository
public interface QuestPersistenceRepository extends JpaRepository<QuestPersistenceEntity, Long> {
    @Query("select quest from QuestPersistenceEntity quest where quest.title = :title")
    Optional<QuestPersistenceEntity> findByTitle(@Param("title") String title);
}
