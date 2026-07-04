package pe.greenminds.ecomind_backend.community.application.commandservices;

import pe.greenminds.ecomind_backend.community.domain.model.aggregates.PostReaction;
import pe.greenminds.ecomind_backend.community.domain.model.commands.CreatePostReactionCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeletePostReactionCommand;
import pe.greenminds.ecomind_backend.community.domain.model.commands.UpdatePostReactionTypeCommand;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface PostReactionCommandService {
    Result<PostReaction, ApplicationError> handle(CreatePostReactionCommand command);

    Result<PostReaction, ApplicationError> handle(UpdatePostReactionTypeCommand command);

    Result<Void, ApplicationError> handle(DeletePostReactionCommand command);
}
