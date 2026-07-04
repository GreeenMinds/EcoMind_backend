package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

@Entity
@Table(
        name = "community_goals",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"community_id", "goal_id"})
        }
)
public class CommunityGoalPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    @Column(name = "goal_id", nullable = false)
    private Long goalId;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "target", nullable = false)
    private Integer target;

    @Column(name = "progress", nullable = false)
    private Integer progress;

    @Column(name = "participants", nullable = false)
    private Integer participants;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CommunityGoalStatus status;

    public Long getCommunityId() { return communityId; }
    public Long getGoalId() { return goalId; }
    public String getDescription() { return description; }
    public Integer getTarget() { return target; }
    public Integer getProgress() { return progress; }
    public Integer getParticipants() { return participants; }
    public CommunityGoalStatus getStatus() { return status; }

    public void setCommunityId(Long communityId) { this.communityId = communityId; }
    public void setGoalId(Long goalId) { this.goalId = goalId; }
    public void setDescription(String description) { this.description = description; }
    public void setTarget(Integer target) { this.target = target; }
    public void setProgress(Integer progress) { this.progress = progress; }
    public void setParticipants(Integer participants) { this.participants = participants; }
    public void setStatus(CommunityGoalStatus status) { this.status = status; }
}
