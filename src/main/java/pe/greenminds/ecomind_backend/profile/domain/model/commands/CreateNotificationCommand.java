package pe.greenminds.ecomind_backend.profile.domain.model.commands;

public record CreateNotificationCommand(
        Long userId,
        String type,
        String title,
        String message,
        String referenceType,
        Long referenceId
) {
}
