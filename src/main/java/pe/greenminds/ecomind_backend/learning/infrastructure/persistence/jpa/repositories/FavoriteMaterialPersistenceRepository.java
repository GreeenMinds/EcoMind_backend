package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.entities.FavoriteMaterialEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteMaterialPersistenceRepository extends JpaRepository<FavoriteMaterialEntity, Long> {
    Optional<FavoriteMaterialEntity> findByUserIdAndMaterialId(Long userId, Long materialId);
    List<FavoriteMaterialEntity> findByUserId(Long userId);
    boolean existsByUserIdAndMaterialId(Long userId, Long materialId);
    void deleteByUserIdAndMaterialId(Long userId, Long materialId);
}
