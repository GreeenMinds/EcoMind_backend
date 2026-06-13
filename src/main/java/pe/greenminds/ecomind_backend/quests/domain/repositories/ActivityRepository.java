package pe.greenminds.ecomind_backend.quests.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByQuestIdOrderByPositionAsc(Long questId);
    boolean existsByQuestIdAndPosition(Long questId, Integer position);
    Integer countByQuestId(Long questId);
}
