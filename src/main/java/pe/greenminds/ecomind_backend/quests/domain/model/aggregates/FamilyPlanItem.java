package pe.greenminds.ecomind_backend.quests.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.OffsetDateTime;
import java.util.Objects;

public class FamilyPlanItem extends AbstractDomainAggregateRoot<FamilyPlanItem> {
    @Getter
    @Setter
    private Long id;

    private Long familyPlanId;
    private Long questId;
    private OffsetDateTime startDate;
    private Long collaborativeSessionId;

    public FamilyPlanItem(
            Long id,
            Long familyPlanId,
            Long questId,
            OffsetDateTime startDate,
            Long collaborativeSessionId
    ) {
        this.id = id;
        this.familyPlanId = Objects.requireNonNull(familyPlanId, "familyPlanId must not be null");
        this.questId = Objects.requireNonNull(questId, "questId must not be null");
        this.startDate = startDate;
        this.collaborativeSessionId = collaborativeSessionId;
    }

    public FamilyPlanItem(Long familyPlanId, Long questId, OffsetDateTime startDate) {
        this(null, familyPlanId, questId, startDate, null);
    }

    public void attachCollaborativeSession(Long collaborativeSessionId) {
        this.collaborativeSessionId = Objects.requireNonNull(
                collaborativeSessionId,
                "collaborativeSessionId must not be null"
        );
    }

    public Long getFamilyPlanId() { return familyPlanId; }
    public Long getQuestId() { return questId; }
    public OffsetDateTime getStartDate() { return startDate; }
    public Long getCollaborativeSessionId() { return collaborativeSessionId; }
}
