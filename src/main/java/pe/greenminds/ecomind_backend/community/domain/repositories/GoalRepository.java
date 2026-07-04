package pe.greenminds.ecomind_backend.community.domain.repositories;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.Goal;

import java.util.List;
import java.util.Optional;

public interface GoalRepository {
    Optional<Goal> findById(Long id);
    List<Goal> findAll();
    Goal save(Goal goal);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
}
