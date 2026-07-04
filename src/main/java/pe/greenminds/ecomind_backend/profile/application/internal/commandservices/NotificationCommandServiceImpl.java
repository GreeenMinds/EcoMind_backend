package pe.greenminds.ecomind_backend.profile.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.application.commandservices.NotificationCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateNotificationCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.MarkAllNotificationsReadCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.MarkNotificationReadCommand;
import pe.greenminds.ecomind_backend.profile.domain.repositories.NotificationRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.util.List;

@Service
public class NotificationCommandServiceImpl implements NotificationCommandService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationCommandServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Result<Notification, ApplicationError> handle(CreateNotificationCommand command) {
        if (!userRepository.existsById(command.userId())) {
            return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        }
        var notification = new Notification(null, command.userId(), command.type(), command.title(),
                command.message(), false, command.referenceType(), command.referenceId(), null, null);
        return Result.success(notificationRepository.save(notification));
    }

    public Result<Notification, ApplicationError> handle(MarkNotificationReadCommand command) {
        var notification = notificationRepository.findById(command.notificationId());
        if (notification.isEmpty()) {
            return Result.failure(ApplicationError.notFound("Notification", command.notificationId().toString()));
        }
        notification.get().markAsRead();
        return Result.success(notificationRepository.save(notification.get()));
    }

    public Result<List<Notification>, ApplicationError> handle(MarkAllNotificationsReadCommand command) {
        if (!userRepository.existsById(command.userId())) {
            return Result.failure(ApplicationError.notFound("User", command.userId().toString()));
        }
        var unread = notificationRepository.search(command.userId(), false);
        unread.forEach(Notification::markAsRead);
        return Result.success(notificationRepository.saveAll(unread));
    }
}
