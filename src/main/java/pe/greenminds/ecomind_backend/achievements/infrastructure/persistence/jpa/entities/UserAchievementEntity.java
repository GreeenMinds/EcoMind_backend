package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.util.Date;

@Entity
@Table(
        name = "user_achievements",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_achievement_user_achievement",
                        columnNames = {"user_id", "achievement_id"}
                )
        }
)
public class UserAchievementEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "achievement_id", nullable = false)
    private Long achievementId;

    @Column(name = "earned_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date earnedAt;

    @Column(name = "newly_unlocked", nullable = false)
    private Boolean newlyUnlocked;

    protected UserAchievementEntity() {}

    public UserAchievementEntity(Long userId, Long achievementId) {
        this.userId = userId;
        this.achievementId = achievementId;
        this.earnedAt = new Date();
        this.newlyUnlocked = true;
    }

    public Long getUserId() { return userId; }
    public Long getAchievementId() { return achievementId; }
    public Date getEarnedAt() { return earnedAt; }
    public Boolean getNewlyUnlocked() { return newlyUnlocked; }

    public void markSeen() {
        this.newlyUnlocked = false;
    }
}
