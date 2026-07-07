package pe.greenminds.ecomind_backend.profile.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.profile.application.commandservices.NotificationCommandService;
import pe.greenminds.ecomind_backend.profile.application.queryservices.NotificationQueryService;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.MarkAllNotificationsReadCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.MarkNotificationReadCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetNotificationsQuery;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.GetUnreadNotificationCountQuery;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.CreateNotificationResource;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.NotificationResource;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.UnreadNotificationCountResource;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.CreateNotificationCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.NotificationResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notification", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Notifications", description = "User notification endpoints")
public class NotificationController {
    private final NotificationCommandService commandService;
    private final NotificationQueryService queryService;

    public NotificationController(NotificationCommandService commandService, NotificationQueryService queryService) {
        this.commandService = commandService;
        this.queryService = queryService;
    }

    @GetMapping
    @Operation(summary = "Get notifications", description = "Retrieve notifications, optionally filtered by user and read status.")
    public ResponseEntity<List<NotificationResource>> getNotifications(
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "is_read", required = false) Boolean read) {
        return ResponseEntity.ok(queryService.handle(new GetNotificationsQuery(userId, read)).stream()
                .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                .toList());
    }

    @GetMapping("/unread-count")
    @Operation(summary = "Get unread notification count", description = "Retrieve the unread notification count for a user.")
    public ResponseEntity<UnreadNotificationCountResource> getUnreadCount(@RequestParam(name = "user_id") Long userId) {
        return ResponseEntity.ok(new UnreadNotificationCountResource(userId,
                queryService.handle(new GetUnreadNotificationCountQuery(userId))));
    }

    @PostMapping
    @Operation(summary = "Create notification", description = "Create a notification for a user.")
    public ResponseEntity<?> createNotification(@Valid @RequestBody CreateNotificationResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(CreateNotificationCommandFromResourceAssembler.toCommandFromResource(resource)),
                NotificationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PatchMapping("/{notificationId}/read")
    @Operation(summary = "Mark notification as read", description = "Mark a single notification as read.")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new MarkNotificationReadCommand(notificationId)),
                NotificationResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @PatchMapping("/read-all")
    @Operation(summary = "Mark all notifications as read", description = "Mark every unread notification for the user as read.")
    public ResponseEntity<?> markAllAsRead(@RequestParam(name = "user_id") Long userId) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new MarkAllNotificationsReadCommand(userId)),
                notifications -> notifications.stream()
                        .map(NotificationResourceFromEntityAssembler::toResourceFromEntity)
                        .toList(),
                HttpStatus.OK);
    }
}
