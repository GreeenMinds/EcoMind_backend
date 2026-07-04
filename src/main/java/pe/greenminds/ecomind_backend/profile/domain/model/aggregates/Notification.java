package pe.greenminds.ecomind_backend.profile.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.OffsetDateTime;
import java.util.Objects;

public class Notification extends AbstractDomainAggregateRoot<Notification> {
    @Getter @Setter private Long id;
    @Getter private Long userId;
    @Getter private String type;
    @Getter private String title;
    @Getter private String message;
    @Getter private boolean read;
    @Getter private String referenceType;
    @Getter private Long referenceId;
    @Getter private OffsetDateTime createdAt;
    @Getter private OffsetDateTime readAt;

    public Notification(Long id, Long userId, String type, String title, String message, Boolean read,
                        String referenceType, Long referenceId, OffsetDateTime createdAt, OffsetDateTime readAt) {
        this.id = id;
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.type = type == null || type.isBlank() ? "general" : type;
        this.title = Objects.requireNonNull(title, "title must not be null");
        this.message = Objects.requireNonNull(message, "message must not be null");
        this.read = read != null && read;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.createdAt = createdAt == null ? OffsetDateTime.now() : createdAt;
        this.readAt = readAt;
    }

    public void markAsRead() {
        if (!this.read) {
            this.read = true;
            this.readAt = OffsetDateTime.now();
        }
    }
}
