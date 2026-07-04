package pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.adapters;

import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.domain.repositories.NotificationRepository;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.assemblers.NotificationPersistenceAssembler;
import pe.greenminds.ecomind_backend.profile.infrastructure.persistence.jpa.repositories.NotificationPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationPersistenceRepository repository;

    public NotificationRepositoryImpl(NotificationPersistenceRepository repository) {
        this.repository = repository;
    }

    public List<Notification> findAll() {
        return repository.findAll().stream().map(NotificationPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public Optional<Notification> findById(Long id) {
        return repository.findById(id).map(NotificationPersistenceAssembler::toDomainFromPersistence);
    }

    public List<Notification> search(Long userId, Boolean read) {
        return repository.search(userId, read).stream().map(NotificationPersistenceAssembler::toDomainFromPersistence).toList();
    }

    public long countUnread(Long userId) {
        return repository.countByUserIdAndReadFalse(userId);
    }

    public Notification save(Notification notification) {
        return NotificationPersistenceAssembler.toDomainFromPersistence(
                repository.save(NotificationPersistenceAssembler.toPersistenceFromDomain(notification)));
    }

    public List<Notification> saveAll(List<Notification> notifications) {
        return repository.saveAll(notifications.stream()
                        .map(NotificationPersistenceAssembler::toPersistenceFromDomain)
                        .toList())
                .stream()
                .map(NotificationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
