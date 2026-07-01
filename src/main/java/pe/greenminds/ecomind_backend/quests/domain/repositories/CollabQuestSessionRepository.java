package pe.greenminds.ecomind_backend.quests.domain.repositories;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestSession;

import java.util.List;
import java.util.Optional;

public interface CollabQuestSessionRepository {
    CollabQuestSession save(CollabQuestSession collabQuestSession);
    Optional<CollabQuestSession> findById(Long id);

    Optional<CollabQuestSession> findByQuestIdAndOwnerUserId(Long questId, Long ownerUserId);

    List<CollabQuestSession> findByOwnerUserId(Long ownerUserId);

    void deleteById(Long id);
}
