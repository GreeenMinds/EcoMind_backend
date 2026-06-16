package pe.greenminds.ecomind_backend.quests.domain.repositories;

import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;

import java.util.List;
import java.util.Optional;

public interface QuestRepository {
    Optional<Quest> findById(Long id);
    List<Quest> findAll();
    List<Quest> search(
            String title,
            Category category,
            QuestType questType,
            Integer minimumAge,
            Theme type
    );
    Quest save(Quest quest);
    void deleteById(Long id);

    boolean existsById(Long id);
}
