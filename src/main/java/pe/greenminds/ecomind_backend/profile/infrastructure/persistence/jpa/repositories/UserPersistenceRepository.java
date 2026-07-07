package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPersistenceRepository extends JpaRepository<UserPersistenceEntity, Long> {
    boolean existsByEmail(String email);
    Optional<UserPersistenceEntity> findByEmail(String email);
    List<UserPersistenceEntity> findByCommunityId(Long communityId);
}
