package pe.greenminds.ecomind_backend.learning.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.learning.domain.model.events.TutorialCompletedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class TutorialProgress extends AbstractDomainAggregateRoot<TutorialProgress> {

    @Getter
    @Setter
    private Long id;

    private Long userId;
    private Integer currentStep;
    private Integer totalSteps;
    private boolean completed;
    private boolean skipped;
    private LocalDateTime completedAt;

    public TutorialProgress(
            Long id,
            Long userId,
            Integer currentStep,
            Integer totalSteps,
            boolean completed,
            boolean skipped,
            LocalDateTime completedAt
    ) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.currentStep = Objects.requireNonNull(currentStep, "currentStep must not be null");
        this.totalSteps = Objects.requireNonNull(totalSteps, "totalSteps must not be null");
        this.completed = completed;
        this.skipped = skipped;
        this.completedAt = completedAt;
    }

    public TutorialProgress(
            Long userId,
            Integer currentStep,
            Integer totalSteps,
            boolean completed,
            boolean skipped,
            LocalDateTime completedAt
    ) {
        this(null, userId, currentStep, totalSteps, completed, skipped, completedAt);
    }

    public Long getUserId() {
        return userId;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }

    public Integer getTotalSteps() {
        return totalSteps;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void advanceStep() {
        if (currentStep < totalSteps) {
            currentStep++;
        }
    }

    public void complete() {
        this.completed = true;
        this.completedAt = LocalDateTime.now();
        registerDomainEvent(TutorialCompletedEvent.from(this));
    }

    public void skip() {
        this.skipped = true;
        this.completed = true;
        this.completedAt = LocalDateTime.now();
    }
}
