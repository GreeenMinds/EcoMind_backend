package pe.greenminds.ecomind_backend.learning.domain.repositories;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;

import java.util.Optional;

public interface TutorialProgressRepository {
    Optional<TutorialProgress> findById(Long id);
    Optional<TutorialProgress> findByUserId(Long userId);
    TutorialProgress save(TutorialProgress tutorialProgress);
    boolean existsByUserId(Long userId);
}
