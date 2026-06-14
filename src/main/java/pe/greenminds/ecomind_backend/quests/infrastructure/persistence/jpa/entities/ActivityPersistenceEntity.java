package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name="activities")
public class ActivityPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "questId", nullable=false)
    private Long questId;

    @Column(name = "description")
    private String description;

    @Positive
    @Column(name = "position", nullable=false)
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable=false)
    private ActivityType type;

    @Column(name = "image")
    private String image;

    public Long getQuestId() {
        return questId;
    }

    public void setQuestId(Long questId) {
        this.questId = questId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
