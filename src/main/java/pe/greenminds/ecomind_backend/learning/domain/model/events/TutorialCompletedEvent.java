package pe.greenminds.ecomind_backend.learning.domain.model.events;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;

public record TutorialCompletedEvent(
        Long userId
) {
    public static TutorialCompletedEvent from(TutorialProgress progress) {
        return new TutorialCompletedEvent(
                progress.getUserId()
        );
    }
}
