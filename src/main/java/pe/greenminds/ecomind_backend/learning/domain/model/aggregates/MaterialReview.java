package pe.greenminds.ecomind_backend.learning.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.learning.domain.model.events.MaterialReviewedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class MaterialReview extends AbstractDomainAggregateRoot<MaterialReview> {

    @Getter
    @Setter
    private Long id;

    private Long userId;
    private Long materialId;
    private LocalDateTime reviewedAt;

    public MaterialReview(
            Long id,
            Long userId,
            Long materialId,
            LocalDateTime reviewedAt
    ) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.materialId = Objects.requireNonNull(materialId, "materialId must not be null");
        this.reviewedAt = Objects.requireNonNull(reviewedAt, "reviewedAt must not be null");
    }

    public MaterialReview(
            Long userId,
            Long materialId,
            LocalDateTime reviewedAt
    ) {
        this(null, userId, materialId, reviewedAt);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public void onCreated() {
        registerDomainEvent(MaterialReviewedEvent.from(this));
    }
}
