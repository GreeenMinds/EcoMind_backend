package pe.greenminds.ecomind_backend.profile.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.queryservices.NotificationQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetNotificationsQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetUnreadNotificationCountQuery;
import pe.greenminds.ecomind_backend.profile.domain.repositories.NotificationRepository;

import java.util.List;

@Service
public class NotificationQueryServiceImpl implements NotificationQueryService {
    private final NotificationRepository repository;

    public NotificationQueryServiceImpl(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<Notification> handle(GetNotificationsQuery query) {
        return repository.search(query.userId(), query.read());
    }

    public long handle(GetUnreadNotificationCountQuery query) {
        return repository.countUnread(query.userId());
    }
}
