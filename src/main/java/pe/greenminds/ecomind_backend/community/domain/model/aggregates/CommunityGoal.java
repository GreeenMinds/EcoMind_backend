package pe.greenminds.ecomind_backend.community.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.CommunityGoalStatus;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.util.Objects;

public class CommunityGoal extends AbstractDomainAggregateRoot<CommunityGoal> {
    private static final int COMPLETED_PROGRESS = 100;

    @Getter
    @Setter
    private Long id;

    private Long communityId;
    private Long goalId;
    private String description;
    private Integer target;
    private Integer progress;
    private Integer participants;
    private CommunityGoalStatus status;

    public CommunityGoal(Long id, Long communityId, Long goalId, String description, Integer target, Integer progress, Integer participants, CommunityGoalStatus status) {
        this.id = id;
        this.communityId = Objects.requireNonNull(communityId, "communityId must not be null");
        this.goalId = Objects.requireNonNull(goalId, "goalId must not be null");
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
        this.progress = Objects.requireNonNull(progress, "progress must not be null");
        this.participants = Objects.requireNonNull(participants, "participants must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        validateProgress();
    }

    public CommunityGoal(Long communityId, Long goalId, String description, Integer target, Integer progress, Integer participants, CommunityGoalStatus status) {
        this(null, communityId, goalId, description, target, progress, participants, status);
    }

    public Long getCommunityId() { return communityId; }
    public Long getGoalId() { return goalId; }
    public String getDescription() { return description; }
    public Integer getTarget() { return target; }
    public Integer getProgress() { return progress; }
    public Integer getParticipants() { return participants; }
    public CommunityGoalStatus getStatus() { return status; }

    public CommunityGoal updateInformation(String description, Integer target, Integer progress, Integer participants, CommunityGoalStatus status) {
        this.description = Objects.requireNonNull(description, "description must not be null");
        this.target = Objects.requireNonNull(target, "target must not be null");
        this.progress = Objects.requireNonNull(progress, "progress must not be null");
        this.participants = Objects.requireNonNull(participants, "participants must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        validateProgress();
        return this;
    }

    public CommunityGoal incrementProgress(Integer increment) {
        Objects.requireNonNull(increment, "increment must not be null");

        if (increment != 1) {
            throw new IllegalArgumentException("increment must be 1");
        }

        if (this.progress >= this.target) {
            this.status = CommunityGoalStatus.COMPLETED;
            return this;
        }

        this.progress = Math.min(this.progress + 1, this.target);

        if (this.progress >= this.target) {
            this.status = CommunityGoalStatus.COMPLETED;
        }

        return this;
    }

    public CommunityGoal updateStatus(CommunityGoalStatus status) {
        Objects.requireNonNull(status, "status must not be null");

        if (status != CommunityGoalStatus.COMPLETED) {
            throw new IllegalArgumentException("status can only be changed to completed");
        }

        if (this.progress != COMPLETED_PROGRESS) {
            throw new IllegalArgumentException("progress must be 100 to complete the community goal");
        }

        this.status = status;
        return this;
    }

    private void validateProgress() {
        if (target <= 0) {
            throw new IllegalArgumentException("target must be greater than zero");
        }

        if (progress < 0) {
            throw new IllegalArgumentException("progress must be positive or zero");
        }

        if (participants < 0) {
            throw new IllegalArgumentException("participants must be positive or zero");
        }

        if (progress > target) {
            throw new IllegalArgumentException("progress must not be greater than target");
        }

        if (progress >= target) {
            status = CommunityGoalStatus.COMPLETED;
        }
    }
}
