package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.TutorialProgressEntity;

import java.util.Optional;

@Repository
public interface TutorialProgressPersistenceRepository extends JpaRepository<TutorialProgressEntity, Long> {

    Optional<TutorialProgressEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
