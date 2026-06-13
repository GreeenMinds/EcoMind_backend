package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.QuestUserStatus;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.time.LocalDateTime;

@Entity
@Table(name = "quest_users")
public class QuestUser extends AuditableAbstractAggregateRoot<QuestUser> {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quest_id", nullable = false)
    private Long questId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private QuestUserStatus status;

    @Column(nullable = false)
    private Integer progress;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "collaborative_session_id")
    private Long collaborativeSessionId;

    protected QuestUser() {}

    public QuestUser(Long userId, Long questId) {
        this.userId = userId;
        this.questId = questId;
        this.status = QuestUserStatus.IN_PROGRESS;
        this.progress = 0;
        this.startDate = LocalDateTime.now();
    }

    public QuestUser(Long userId, Long questId, Long collaborativeSessionId) {
        this(userId, questId);
        this.collaborativeSessionId = collaborativeSessionId;
    }

    public Long getUserId() { return userId; }
    public Long getQuestId() { return questId; }
    public QuestUserStatus getStatus() { return status; }
    public Integer getProgress() { return progress; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public Long getCollaborativeSessionId() { return collaborativeSessionId; }

    public void updateInformation(QuestUserStatus status, Integer progress,
                                  LocalDateTime endDate, Long collaborativeSessionId) {
        if (status != null) this.status = status;
        if (progress != null) this.progress = progress;
        if (endDate != null) this.endDate = endDate;
        if (collaborativeSessionId != null) this.collaborativeSessionId = collaborativeSessionId;
        if (this.status == QuestUserStatus.COMPLETED) this.endDate = LocalDateTime.now();
    }

    public void updateProgress(Integer progress) {
        this.progress = progress;
    }

    public void complete() {
        this.status = QuestUserStatus.COMPLETED;
        this.progress = 100;
        this.endDate = LocalDateTime.now();
    }

    public void markReadyToComplete() {
        this.status = QuestUserStatus.READY_TO_COMPLETE;
    }
}
