package pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.adapters;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostReactionRepository;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.assemblers.PostReactionPersistenceAssembler;
import pe.greenminds.ecomind_backend.community.infrastructure.persistence.jpa.repositories.PostReactionPersistenceRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostReactionRepositoryImpl implements PostReactionRepository {

    private final PostReactionPersistenceRepository postReactionPersistenceRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public PostReactionRepositoryImpl(
            PostReactionPersistenceRepository postReactionPersistenceRepository,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.postReactionPersistenceRepository = postReactionPersistenceRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Optional<PostReaction> findById(Long id) {
        return postReactionPersistenceRepository.findById(id)
                .map(PostReactionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<PostReaction> search(Long postId, Long userId, PostReactionType reactionType) {
        return postReactionPersistenceRepository.search(postId, userId, reactionType)
                .stream()
                .map(PostReactionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public PostReaction save(PostReaction postReaction) {
        boolean isNew = postReaction.getId() == null;
        var savedEntity = postReactionPersistenceRepository.save(
                PostReactionPersistenceAssembler.toPersistenceFromDomain(postReaction)
        );
        var savedPostReaction = PostReactionPersistenceAssembler.toDomainFromPersistence(savedEntity);

        if (isNew) {
            savedPostReaction.onCreated();
            savedPostReaction.domainEvents().forEach(applicationEventPublisher::publishEvent);
            savedPostReaction.clearDomainEvents();
        }

        return savedPostReaction;
    }

    @Override
    public void deleteById(Long id) {
        postReactionPersistenceRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return postReactionPersistenceRepository.existsById(id);
    }

    @Override
    public boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return postReactionPersistenceRepository.existsByPostIdAndUserId(postId, userId);
    }

    @Override
    public long countByPostId(Long postId) {
        return postReactionPersistenceRepository.countByPostId(postId);
    }
}
