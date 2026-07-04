package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "goals")
public class GoalPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "title", nullable = false, length = 120)
    private String title;

    @Column(name = "unit", nullable = false, length = 50)
    private String unit;

    @Column(name = "quest_category", nullable = false, length = 80)
    private String questCategory;

    public String getTitle() { return title; }
    public String getUnit() { return unit; }
    public String getQuestCategory() { return questCategory; }

    public void setTitle(String title) { this.title = title; }
    public void setUnit(String unit) { this.unit = unit; }
    public void setQuestCategory(String questCategory) { this.questCategory = questCategory; }
}