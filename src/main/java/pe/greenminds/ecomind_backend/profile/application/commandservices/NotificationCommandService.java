package pe.greenminds.ecomind_backend.profile.application.commandservices;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Notification;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateNotificationCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.MarkAllNotificationsReadCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.MarkNotificationReadCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.util.List;

public interface NotificationCommandService {
    Result<Notification, ApplicationError> handle(CreateNotificationCommand command);
    Result<Notification, ApplicationError> handle(MarkNotificationReadCommand command);
    Result<List<Notification>, ApplicationError> handle(MarkAllNotificationsReadCommand command);
}
