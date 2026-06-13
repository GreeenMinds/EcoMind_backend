package pe.greenminds.ecomind_backend.quests.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityUserRepository extends JpaRepository<ActivityUser, Long> {
    List<ActivityUser> findByUserId(Long userId);
    List<ActivityUser> findByActivityId(Long activityId);
    Optional<ActivityUser> findByUserIdAndActivityId(Long userId, Long activityId);
}
