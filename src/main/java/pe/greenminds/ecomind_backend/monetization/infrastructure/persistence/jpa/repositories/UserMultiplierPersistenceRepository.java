package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.UserMultiplierPersistenceEntity;

@Repository
public interface UserMultiplierPersistenceRepository extends JpaRepository<UserMultiplierPersistenceEntity, Long> {
}
