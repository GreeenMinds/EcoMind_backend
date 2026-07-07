package pe.greenminds.ecomind_backend.achievements.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.AuditableAbstractPersistenceEntity;

import java.util.Date;

@Entity
@Table(
        name = "community_achievements",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_community_achievement_community_achievement",
                        columnNames = {"community_id", "achievement_id"}
                )
        }
)
public class CommunityAchievementEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "achievement_id", nullable = false)
    private Long achievementId;

    @Column(name = "earned_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date earnedAt;

    @Column(name = "newly_unlocked", nullable = false)
    private Boolean newlyUnlocked;

    protected CommunityAchievementEntity() {}

    public CommunityAchievementEntity(Long communityId, Long achievementId) {
        this.communityId = communityId;
        this.achievementId = achievementId;
        this.earnedAt = new Date();
        this.newlyUnlocked = true;
    }

    public Long getCommunityId() { return communityId; }
    public Long getAchievementId() { return achievementId; }
    public Date getEarnedAt() { return earnedAt; }
    public Boolean getNewlyUnlocked() { return newlyUnlocked; }

    public void markSeen() {
        this.newlyUnlocked = false;
    }
}
