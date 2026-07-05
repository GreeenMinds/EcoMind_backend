package pe.greenminds.ecomind_backend.profile.application.queryservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetNotificationsQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetUnreadNotificationCountQuery;

import java.util.List;

public interface NotificationQueryService {
    List<Notification> handle(GetNotificationsQuery query);
    long handle(GetUnreadNotificationCountQuery query);
}
