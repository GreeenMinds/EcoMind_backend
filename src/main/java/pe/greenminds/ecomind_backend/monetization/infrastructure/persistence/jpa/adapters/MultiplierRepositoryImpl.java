package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.MultiplierRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.MultiplierPersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.MultiplierPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class MultiplierRepositoryImpl implements MultiplierRepository {

    private final MultiplierPersistenceRepository multiplierPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MultiplierRepositoryImpl(MultiplierPersistenceRepository multiplierPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.multiplierPersistenceRepository = multiplierPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<Multiplier> findById(Long id) {
        return multiplierPersistenceRepository.findById(id).map(MultiplierPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Multiplier> findAll() {
        return multiplierPersistenceRepository.findAll().stream().map(MultiplierPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Multiplier save(Multiplier multiplier) {
        boolean isNew = multiplier.getId() == null;
        var savedEntity = multiplierPersistenceRepository.save(MultiplierPersistenceAssembler.toPersistenceFromDomain(multiplier));
        var saved = MultiplierPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        multiplierPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return multiplierPersistenceRepository.existsById(id);
    }
}
