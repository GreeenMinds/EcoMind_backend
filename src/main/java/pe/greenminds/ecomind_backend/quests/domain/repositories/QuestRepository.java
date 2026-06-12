package pe.greenminds.ecomind_backend.quests.domain.repositories;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;

import java.util.List;
import java.util.Optional;

public interface QuestRepository {
    Optional<Quest> findById(Long id);
    Optional<Quest> findByTitle(String title);
    List<Quest> findAll();
    Quest save(Quest quest);
    void deleteById(Long id);

    boolean existsById(Long id);
}
