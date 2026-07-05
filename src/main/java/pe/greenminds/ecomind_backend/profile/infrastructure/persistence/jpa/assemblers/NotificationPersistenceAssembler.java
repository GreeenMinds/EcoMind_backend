package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.entities.NotificationPersistenceEntity;

public class NotificationPersistenceAssembler {
    private NotificationPersistenceAssembler() {}

    public static Notification toDomainFromPersistence(NotificationPersistenceEntity entity) {
        return new Notification(entity.getId(), entity.getUserId(), entity.getType(), entity.getTitle(),
                entity.getMessage(), entity.isRead(), entity.getReferenceType(), entity.getReferenceId(),
                entity.getCreatedAt(), entity.getReadAt());
    }

    public static NotificationPersistenceEntity toPersistenceFromDomain(Notification notification) {
        var entity = new NotificationPersistenceEntity();
        entity.setId(notification.getId());
        entity.setUserId(notification.getUserId());
        entity.setType(notification.getType());
        entity.setTitle(notification.getTitle());
        entity.setMessage(notification.getMessage());
        entity.setRead(notification.isRead());
        entity.setReferenceType(notification.getReferenceType());
        entity.setReferenceId(notification.getReferenceId());
        entity.setCreatedAt(notification.getCreatedAt());
        entity.setReadAt(notification.getReadAt());
        return entity;
    }
}
