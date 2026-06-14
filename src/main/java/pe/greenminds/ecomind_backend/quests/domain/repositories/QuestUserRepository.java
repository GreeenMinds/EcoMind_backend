package pe.greenminds.ecomind_backend.quests.domain.repositories;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.QuestUser;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestStatus;

import java.util.List;
import java.util.Optional;

public interface QuestUserRepository {
    QuestUser save(QuestUser questUser);
    Optional<QuestUser> findById(Long id);

    Optional<QuestUser> findByUserIdAndQuestId(Long userId, Long questId);

    boolean existsByUserIdAndQuestId(Long userId, Long questId);

    void deleteById(Long id);

    List<QuestUser> findByUserIdAndStatus(Long userId, QuestStatus questStatus);

}
