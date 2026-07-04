package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class Goal extends AbstractDomainAggregateRoot<Goal> {
    @Getter
    @Setter
    private Long id;

    private String title;
    private String unit;
    private String questCategory;

    public Goal(Long id, String title, String unit, String questCategory) {
        this.id = id;
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.questCategory = Objects.requireNonNull(questCategory, "questCategory must not be null");
    }

    public Goal(String title, String unit, String questCategory) {
        this(null, title, unit, questCategory);
    }

    public String getTitle() { return title; }
    public String getUnit() { return unit; }
    public String getQuestCategory() { return questCategory; }

    public Goal updateInformation(String title, String unit, String questCategory) {
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.unit = Objects.requireNonNull(unit, "unit must not be null");
        this.questCategory = Objects.requireNonNull(questCategory, "questCategory must not be null");
        return this;
    }
}