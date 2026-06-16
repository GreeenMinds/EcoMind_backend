package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers.CosmeticPersistenceAssembler;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.repositories.CosmeticPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class CosmeticRepositoryImpl implements CosmeticRepository {

    private final CosmeticPersistenceRepository cosmeticPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CosmeticRepositoryImpl(CosmeticPersistenceRepository cosmeticPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.cosmeticPersistenceRepository = cosmeticPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<Cosmetic> findById(Long id) {
        return cosmeticPersistenceRepository.findById(id).map(CosmeticPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Cosmetic> findAll() {
        return cosmeticPersistenceRepository.findAll().stream().map(CosmeticPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Cosmetic save(Cosmetic cosmetic) {
        boolean isNew = cosmetic.getId() == null;
        var savedEntity = cosmeticPersistenceRepository.save(CosmeticPersistenceAssembler.toPersistenceFromDomain(cosmetic));
        var saved = CosmeticPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            saved.onCreated();
            saved.domainEvents().forEach(applicationEventPublisher::publishEvent);
            saved.clearDomainEvents();
        }
        return saved;
    }

    @Override
    public void deleteById(Long id) {
        cosmeticPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return cosmeticPersistenceRepository.existsById(id);
    }
}
