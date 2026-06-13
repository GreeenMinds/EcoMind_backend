package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_users")
public class ActivityUser extends AuditableAbstractAggregateRoot<ActivityUser> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(nullable = false)
    private Integer progress;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "collaborative_session_id")
    private Long collaborativeSessionId;

    protected ActivityUser() {}

    public ActivityUser(Long userId, Long activityId) {
        this.userId = userId;
        this.activityId = activityId;
        this.progress = 0;
    }

    public ActivityUser(Long userId, Long activityId, Long collaborativeSessionId) {
        this(userId, activityId);
        this.collaborativeSessionId = collaborativeSessionId;
    }

    public Long getUserId() { return userId; }
    public Long getActivityId() { return activityId; }
    public Integer getProgress() { return progress; }
    public LocalDateTime getEndDate() { return endDate; }
    public Long getCollaborativeSessionId() { return collaborativeSessionId; }

    public void updateInformation(Integer progress, LocalDateTime endDate, Long collaborativeSessionId) {
        if (progress != null) this.progress = progress;
        if (endDate != null) this.endDate = endDate;
        if (collaborativeSessionId != null) this.collaborativeSessionId = collaborativeSessionId;
    }

    public void updateProgress(Integer progress) {
        this.progress = progress;
    }

    public void complete() {
        this.progress = 100;
        this.endDate = LocalDateTime.now();
    }
}
