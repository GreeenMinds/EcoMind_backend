package pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.entities.PasswordResetTokenPersistenceEntity;

import java.util.Optional;

@Repository
public interface PasswordResetTokenPersistenceRepository extends JpaRepository<PasswordResetTokenPersistenceEntity, Long> {
    Optional<PasswordResetTokenPersistenceEntity> findByTokenHash(String tokenHash);
}
