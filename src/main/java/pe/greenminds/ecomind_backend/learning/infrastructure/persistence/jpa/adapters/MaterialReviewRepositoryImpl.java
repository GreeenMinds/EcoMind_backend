package pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.MaterialReview;
import pe.greenminds.ecomind_backend.learning.domain.repositories.MaterialReviewRepository;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.assemblers.MaterialReviewPersistenceAssembler;
import pe.greenminds.ecomind_backend.learning.infrastructure.persistence.jpa.repositories.MaterialReviewPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class MaterialReviewRepositoryImpl implements MaterialReviewRepository {
    private final MaterialReviewPersistenceRepository materialReviewPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public MaterialReviewRepositoryImpl(MaterialReviewPersistenceRepository materialReviewPersistenceRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.materialReviewPersistenceRepository = materialReviewPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<MaterialReview> findById(Long id) {
        return materialReviewPersistenceRepository.findById(id).map(MaterialReviewPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<MaterialReview> findByUserId(Long userId) {
        return materialReviewPersistenceRepository.findByUserId(userId).stream().map(MaterialReviewPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public Optional<MaterialReview> findByUserIdAndMaterialId(Long userId, Long materialId) {
        return materialReviewPersistenceRepository.findByUserIdAndMaterialId(userId, materialId).map(MaterialReviewPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public boolean existsByUserIdAndMaterialId(Long userId, Long materialId) {
        return materialReviewPersistenceRepository.existsByUserIdAndMaterialId(userId, materialId);
    }

    @Override
    public MaterialReview save(MaterialReview materialReview) {
        boolean isNew = materialReview.getId() == null;
        var savedEntity = materialReviewPersistenceRepository.save(MaterialReviewPersistenceAssembler.toPersistenceFromDomain(materialReview));
        var savedReview = MaterialReviewPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedReview.onCreated();
            savedReview.domainEvents().forEach(applicationEventPublisher::publishEvent);
            savedReview.clearDomainEvents();
        }
        return savedReview;
    }
}
