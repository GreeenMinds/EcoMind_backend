package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.util.Date;

@Entity
@Table(
        name = "family_achievements",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_family_achievement_family_achievement",
                        columnNames = {"family_id", "achievement_id"}
                )
        }
)
public class FamilyAchievementEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "family_id", nullable = false)
    private Long familyId;

    @Column(name = "achievement_id", nullable = false)
    private Long achievementId;

    @Column(name = "earned_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date earnedAt;

    @Column(name = "newly_unlocked", nullable = false)
    private Boolean newlyUnlocked;

    protected FamilyAchievementEntity() {}

    public FamilyAchievementEntity(Long familyId, Long achievementId) {
        this.familyId = familyId;
        this.achievementId = achievementId;
        this.earnedAt = new Date();
        this.newlyUnlocked = true;
    }

    public Long getFamilyId() { return familyId; }
    public Long getAchievementId() { return achievementId; }
    public Date getEarnedAt() { return earnedAt; }
    public Boolean getNewlyUnlocked() { return newlyUnlocked; }

    public void markSeen() {
        this.newlyUnlocked = false;
    }
}
