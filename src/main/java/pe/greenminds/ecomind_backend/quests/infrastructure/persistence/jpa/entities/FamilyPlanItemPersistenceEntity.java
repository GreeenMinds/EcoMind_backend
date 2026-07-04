package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "family_plan_items")
public class FamilyPlanItemPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "family_plan_id", nullable = false)
    private Long familyPlanId;

    @Column(name = "quest_id", nullable = false)
    private Long questId;

    @Column(name = "collaborative_session_id")
    private Long collaborativeSessionId;

    public Long getFamilyPlanId() { return familyPlanId; }
    public void setFamilyPlanId(Long familyPlanId) { this.familyPlanId = familyPlanId; }
    public Long getQuestId() { return questId; }
    public void setQuestId(Long questId) { this.questId = questId; }
    public Long getCollaborativeSessionId() { return collaborativeSessionId; }
    public void setCollaborativeSessionId(Long collaborativeSessionId) {
        this.collaborativeSessionId = collaborativeSessionId;
    }
}
