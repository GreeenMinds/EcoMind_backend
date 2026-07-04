package pe.greenminds.ecomind_backend.profile.domain.repositories;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    List<Notification> findAll();
    Optional<Notification> findById(Long id);
    List<Notification> search(Long userId, Boolean read);
    long countUnread(Long userId);
    Notification save(Notification notification);
    List<Notification> saveAll(List<Notification> notifications);
}
