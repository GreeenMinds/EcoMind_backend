package pe.greenminds.ecomind_backend.quests.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestUserRepository extends JpaRepository<QuestUser, Long> {
    List<QuestUser> findByUserId(Long userId);
    List<QuestUser> findByQuestId(Long questId);
    Optional<QuestUser> findByUserIdAndQuestId(Long userId, Long questId);
}
