package pe.greenminds.ecomind_backend.learning.application.internal.eventhandlers;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.learning.domain.model.events.MaterialReviewedEvent;
import pe.greenminds.ecomind_backend.learning.domain.model.events.TutorialCompletedEvent;
import pe.greenminds.ecomind_backend.learning.domain.repositories.EducationalMaterialRepository;
import pe.greenminds.ecomind_backend.profile.application.commandservices.NotificationCommandService;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.CreateNotificationCommand;

@Component
public class LearningNotificationEventHandler {

    private final NotificationCommandService notificationCommandService;
    private final EducationalMaterialRepository educationalMaterialRepository;

    public LearningNotificationEventHandler(
            NotificationCommandService notificationCommandService,
            EducationalMaterialRepository educationalMaterialRepository
    ) {
        this.notificationCommandService = notificationCommandService;
        this.educationalMaterialRepository = educationalMaterialRepository;
    }

    @EventListener
    public void on(MaterialReviewedEvent event) {
        var material = educationalMaterialRepository.findById(event.materialId());
        var title = material.isPresent()
                ? "¡Has completado \"" + material.get().getTitle() + "\"!"
                : "Material educativo completado";
        notificationCommandService.handle(new CreateNotificationCommand(
                event.userId(),
                "learning",
                title,
                "Sigue aprendiendo y revisa más materiales ecológicos.",
                "material",
                event.materialId()
        ));
    }

    @EventListener
    public void on(TutorialCompletedEvent event) {
        notificationCommandService.handle(new CreateNotificationCommand(
                event.userId(),
                "learning",
                "¡Tutorial completado!",
                "Has completado el tutorial de EcoMind. ¡Ahora puedes explorar todas las funcionalidades!",
                "tutorial",
                null
        ));
    }
}
