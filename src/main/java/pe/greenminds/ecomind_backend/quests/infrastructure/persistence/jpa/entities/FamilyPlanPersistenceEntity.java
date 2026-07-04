package pe.greenminds.ecomind_backend.quests.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.FamilyPlanStatus;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(name = "family_plans")
public class FamilyPlanPersistenceEntity extends AuditableAbstractPersistenceEntity {
    @Column(name = "family_id", nullable = false)
    private Long familyId;

    @Column(name = "owner_user_id", nullable = false)
    private Long ownerUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FamilyPlanStatus status;

    public Long getFamilyId() { return familyId; }
    public void setFamilyId(Long familyId) { this.familyId = familyId; }
    public Long getOwnerUserId() { return ownerUserId; }
    public void setOwnerUserId(Long ownerUserId) { this.ownerUserId = ownerUserId; }
    public FamilyPlanStatus getStatus() { return status; }
    public void setStatus(FamilyPlanStatus status) { this.status = status; }
}
