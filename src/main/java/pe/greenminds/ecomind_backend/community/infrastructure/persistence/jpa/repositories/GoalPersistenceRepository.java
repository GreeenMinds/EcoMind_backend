package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities.GoalPersistenceEntity;

@Repository
public interface GoalPersistenceRepository extends JpaRepository<GoalPersistenceEntity, Long> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
}