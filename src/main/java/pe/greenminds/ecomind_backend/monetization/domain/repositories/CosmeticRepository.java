package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;

import java.util.List;
import java.util.Optional;

public interface CosmeticRepository {
    Optional<Cosmetic> findById(Long id);
    List<Cosmetic> findAll();
    Cosmetic save(Cosmetic cosmetic);
    void deleteById(Long id);
    boolean existsById(Long id);
}
