package pe.greenminds.ecomind_backend.community.application.internal.commandservices;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.application.commandservices.PostReactionCommandService;
import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreatePostReactionCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeletePostReactionCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdatePostReactionTypeCommand;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostReactionRepository;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

@Service
public class PostReactionCommandServiceImpl implements PostReactionCommandService {

    private final PostReactionRepository postReactionRepository;
    private final PostRepository postRepository;

    public PostReactionCommandServiceImpl(
            PostReactionRepository postReactionRepository,
            PostRepository postRepository
    ) {
        this.postReactionRepository = postReactionRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Result<PostReaction, ApplicationError> handle(CreatePostReactionCommand command) {
        if (!postRepository.existsById(command.postId())) {
            return Result.failure(
                    ApplicationError.notFound("Post", command.postId().toString())
            );
        }

        if (postReactionRepository.existsByPostIdAndUserId(command.postId(), command.userId())) {
            return Result.failure(
                    ApplicationError.conflict("PostReaction", "The user already reacted to this post")
            );
        }

        try {
            var postReaction = new PostReaction(
                    command.postId(),
                    command.userId(),
                    command.reactionType()
            );

            return Result.success(postReactionRepository.save(postReaction));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("PostReaction", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("PostReaction creation", exception.getMessage())
            );
        }
    }

    @Transactional
    @Override
    public Result<PostReaction, ApplicationError> handle(UpdatePostReactionTypeCommand command) {
        var postReaction = postReactionRepository.findById(command.id());

        if (postReaction.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("PostReaction", command.id().toString())
            );
        }

        try {
            postReaction.get().updateType(command.reactionType());
            return Result.success(postReactionRepository.save(postReaction.get()));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("PostReaction", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("PostReaction type update", exception.getMessage())
            );
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeletePostReactionCommand command) {
        try {
            if (!postReactionRepository.existsById(command.id())) {
                return Result.failure(
                        ApplicationError.notFound("PostReaction", command.id().toString())
                );
            }

            postReactionRepository.deleteById(command.id());
            return Result.success(null);
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected("PostReaction deletion", exception.getMessage())
            );
        }
    }
}
