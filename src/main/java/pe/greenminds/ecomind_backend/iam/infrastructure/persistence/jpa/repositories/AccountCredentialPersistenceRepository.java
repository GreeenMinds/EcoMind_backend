package pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.entities.AccountCredentialPersistenceEntity;

import java.util.Optional;

@Repository
public interface AccountCredentialPersistenceRepository extends JpaRepository<AccountCredentialPersistenceEntity, Long> {
    boolean existsByEmail(String email);
    Optional<AccountCredentialPersistenceEntity> findByEmail(String email);
    Optional<AccountCredentialPersistenceEntity> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
