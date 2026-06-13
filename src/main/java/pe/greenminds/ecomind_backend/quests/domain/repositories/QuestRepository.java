package pe.greenminds.ecomind_backend.quests.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.Quest;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Category;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestType;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.Theme;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    @Query("""
    SELECT q FROM Quest q
    WHERE (:title = '' OR LOWER(q.title)
           LIKE LOWER(CONCAT('%', :title, '%')))
      AND (:category IS NULL OR q.category = :category)
      AND (:questType IS NULL OR q.type = :questType)
      AND (:theme IS NULL OR q.theme = :theme)
      AND (:age IS NULL OR q.age <= :age)
    """)
    List<Quest> search(
            @Param("title") String title,
            @Param("category") Category category,
            @Param("questType") QuestType questType,
            @Param("age") Integer age,
            @Param("theme") Theme theme
    );
}
