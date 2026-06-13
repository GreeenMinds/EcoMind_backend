package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "activities")
public class Activity extends AuditableAbstractAggregateRoot<Activity> {

    @Column(name = "quest_id", nullable = false)
    private Long questId;

    private String description;

    @Positive
    @Column(nullable = false)
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActivityType type;

    @Column(name = "image_url")
    private String image;

    protected Activity() {}

    public Activity(Long questId, String description, Integer position, ActivityType type, String image) {
        this.questId = questId;
        this.description = description;
        this.position = position;
        this.type = type;
        this.image = image;
    }

    public Long getQuestId() { return questId; }
    public String getDescription() { return description; }
    public Integer getPosition() { return position; }
    public ActivityType getType() { return type; }
    public String getImage() { return image; }

    public void setPosition(Integer position) { this.position = position; }

    public void updateInformation(Long questId, String description, Integer position, ActivityType type, String image) {
        this.questId = questId;
        this.description = description;
        this.position = position;
        this.type = type;
        this.image = image;
    }
}
