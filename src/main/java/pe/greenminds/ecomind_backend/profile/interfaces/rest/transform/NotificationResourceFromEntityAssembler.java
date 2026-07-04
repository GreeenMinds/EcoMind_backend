package pe.greenminds.ecomind_backend.profile.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.NotificationResource;

public class NotificationResourceFromEntityAssembler {
    private NotificationResourceFromEntityAssembler() {}

    public static NotificationResource toResourceFromEntity(Notification notification) {
        return new NotificationResource(
                notification.getId(),
                notification.getUserId(),
                notification.getType(),
                notification.getTitle(),
                notification.getMessage(),
                notification.isRead(),
                notification.getReferenceType(),
                notification.getReferenceId(),
                notification.getCreatedAt() == null ? null : notification.getCreatedAt().toString(),
                notification.getReadAt() == null ? null : notification.getReadAt().toString()
        );
    }
}
