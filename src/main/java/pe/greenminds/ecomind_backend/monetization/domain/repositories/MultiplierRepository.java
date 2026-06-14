package pe.greenminds.ecomind_backend.monetization.domain.repositories;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;

import java.util.List;
import java.util.Optional;

public interface MultiplierRepository {
    Optional<Multiplier> findById(Long id);
    List<Multiplier> findAll();
    Multiplier save(Multiplier multiplier);
    void deleteById(Long id);
    boolean existsById(Long id);
}
