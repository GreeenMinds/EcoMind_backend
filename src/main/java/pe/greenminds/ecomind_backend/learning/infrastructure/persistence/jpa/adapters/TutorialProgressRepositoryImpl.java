package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.TutorialProgress;
import pe.greenminds.ecomind_backend.learning.domain.repositories.TutorialProgressRepository;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.assemblers.TutorialProgressPersistenceAssembler;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories.TutorialProgressPersistenceRepository;

import java.util.Optional;

@Repository
public class TutorialProgressRepositoryImpl implements TutorialProgressRepository {
    private final TutorialProgressPersistenceRepository tutorialProgressPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public TutorialProgressRepositoryImpl(TutorialProgressPersistenceRepository tutorialProgressPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.tutorialProgressPersistenceRepository = tutorialProgressPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<TutorialProgress> findById(Long id) {
        return tutorialProgressPersistenceRepository.findById(id).map(TutorialProgressPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<TutorialProgress> findByUserId(Long userId) {
        return tutorialProgressPersistenceRepository.findByUserId(userId).map(TutorialProgressPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return tutorialProgressPersistenceRepository.existsByUserId(userId);
    }

    @Override
    public TutorialProgress save(TutorialProgress tutorialProgress) {
        boolean isNew = tutorialProgress.getId() == null;
        var savedEntity = tutorialProgressPersistenceRepository.save(TutorialProgressPersistenceAssembler.toPersistenceFromDomain(tutorialProgress));
        var savedProgress = TutorialProgressPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedProgress.domainEvents().forEach(applicationEventPublisher::publishEvent);
            savedProgress.clearDomainEvents();
        }
        return savedProgress;
    }
}
