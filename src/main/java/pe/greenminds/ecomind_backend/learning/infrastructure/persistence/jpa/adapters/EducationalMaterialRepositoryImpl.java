package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialCategory;
import pe.greenminds.ecomind_backend.learning.domain.model.valueobjects.MaterialType;
import pe.greenminds.ecomind_backend.learning.domain.repositories.EducationalMaterialRepository;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.assemblers.EducationalMaterialPersistenceAssembler;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories.EducationalMaterialPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class EducationalMaterialRepositoryImpl implements EducationalMaterialRepository {
    private final EducationalMaterialPersistenceRepository educationalMaterialPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EducationalMaterialRepositoryImpl(EducationalMaterialPersistenceRepository educationalMaterialPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.educationalMaterialPersistenceRepository = educationalMaterialPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<EducationalMaterial> findById(Long id) {
        return educationalMaterialPersistenceRepository.findById(id).map(EducationalMaterialPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<EducationalMaterial> findAll() {
        return educationalMaterialPersistenceRepository.findAll().stream().map(EducationalMaterialPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public List<EducationalMaterial> search(String title, MaterialCategory category, MaterialType materialType) {
        String normalizedTitle = title == null ? "" : title.trim();
        return educationalMaterialPersistenceRepository
                .search(normalizedTitle, category, materialType)
                .stream()
                .map(EducationalMaterialPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public EducationalMaterial save(EducationalMaterial educationalMaterial) {
        boolean isNew = educationalMaterial.getId() == null;
        var savedEntity = educationalMaterialPersistenceRepository.save(EducationalMaterialPersistenceAssembler.toPersistenceFromDomain(educationalMaterial));
        var savedMaterial = EducationalMaterialPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedMaterial.onCreated();
            savedMaterial.domainEvents().forEach(applicationEventPublisher::publishEvent);
            savedMaterial.clearDomainEvents();
        }
        return savedMaterial;
    }

    @Override
    public void deleteById(Long id) {
        educationalMaterialPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return educationalMaterialPersistenceRepository.existsById(id);
    }
}
