package pe.greenminds.ecomind_backend.community.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.community.domain.model.events.EventCreatedEvent;
import pe.greenminds.ecomind_backend.profile.application.commandservices.NotificationCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateNotificationCommand;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

@Component
public class EventNotificationEventHandler {

    private final NotificationCommandService notificationCommandService;
    private final FamilyUserRepository familyUserRepository;
    private final UserRepository userRepository;

    public EventNotificationEventHandler(
            NotificationCommandService notificationCommandService,
            FamilyUserRepository familyUserRepository,
            UserRepository userRepository
    ) {
        this.notificationCommandService = notificationCommandService;
        this.familyUserRepository = familyUserRepository;
        this.userRepository = userRepository;
    }

    @EventListener
    public void on(EventCreatedEvent event) {
        var allFamilyUsers = familyUserRepository.findAll();
        var parentUserIds = allFamilyUsers.stream()
                .filter(fu -> fu.getFamilyRole() == FamilyRole.PARENT)
                .map(fu -> fu.getUserId())
                .toList();

        var communityUsers = userRepository.findAll().stream()
                .filter(u -> event.communityId().equals(u.getCommunityId()))
                .toList();

        for (var user : communityUsers) {
            if (parentUserIds.contains(user.getId())) {
                notificationCommandService.handle(new CreateNotificationCommand(
                        user.getId(),
                        "community",
                        "Nuevo evento: " + event.name(),
                        "Se ha creado un nuevo evento en tu comunidad.",
                        "event",
                        event.eventId()
                ));
            }
        }
    }
}
