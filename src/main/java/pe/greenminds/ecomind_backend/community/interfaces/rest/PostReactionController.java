package pe.greenminds.ecomind_backend.community.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.greenminds.ecomind_backend.community.application.commandservices.PostReactionCommandService;
import pe.greenminds.ecomind_backend.community.application.queryservices.PostReactionQueryService;
import pe.greenminds.ecomind_backend.community.domain.model.commands.DeletePostReactionCommand;
import pe.greenminds.ecomind_backend.community.domain.model.queries.GetPostReactionByIdQuery;
import pe.greenminds.ecomind_backend.community.domain.model.queries.SearchPostReactionsQuery;
import pe.greenminds.ecomind_backend.community.domain.model.valueobjects.PostReactionType;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.CreatePostReactionResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.PostReactionResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.resources.UpdatePostReactionTypeResource;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.CreatePostReactionCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.PostReactionResourceFromEntityAssembler;
import pe.greenminds.ecomind_backend.community.interfaces.rest.transform.UpdatePostReactionTypeCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/community/post-reactions", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Post Reactions", description = "Community post reaction management endpoints")
public class PostReactionController {

    private final PostReactionCommandService postReactionCommandService;
    private final PostReactionQueryService postReactionQueryService;

    public PostReactionController(
            PostReactionCommandService postReactionCommandService,
            PostReactionQueryService postReactionQueryService
    ) {
        this.postReactionCommandService = postReactionCommandService;
        this.postReactionQueryService = postReactionQueryService;
    }

    @GetMapping
    public ResponseEntity<List<PostReactionResource>> searchPostReactions(
            @RequestParam(name = "post_id", required = false) Long postId,
            @RequestParam(name = "user_id", required = false) Long userId,
            @RequestParam(name = "reaction_type", required = false) String reactionType
    ) {
        var parsedReactionType = reactionType == null ? null : PostReactionType.fromValue(reactionType);
        var query = new SearchPostReactionsQuery(postId, userId, parsedReactionType);

        var resources = postReactionQueryService.handle(query)
                .stream()
                .map(PostReactionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostReactionResource> getPostReactionById(@PathVariable Long id) {
        return postReactionQueryService.handle(new GetPostReactionByIdQuery(id))
                .map(PostReactionResourceFromEntityAssembler::toResourceFromEntity)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createPostReaction(@Valid @RequestBody CreatePostReactionResource resource) {
        var command = CreatePostReactionCommandFromResourceAssembler.toCommandFromResource(resource);
        var result = postReactionCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostReactionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED
        );
    }

    @PatchMapping("/{id}/type")
    public ResponseEntity<?> updatePostReactionType(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePostReactionTypeResource resource
    ) {
        var command = UpdatePostReactionTypeCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var result = postReactionCommandService.handle(command);

        return ResponseEntityAssembler.toResponseEntityFromResult(
                result,
                PostReactionResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePostReaction(@PathVariable Long id) {
        var result = postReactionCommandService.handle(new DeletePostReactionCommand(id));

        return switch (result) {
            case Result.Success<Void, ApplicationError> ignored ->
                    ResponseEntity.noContent().build();

            case Result.Failure<Void, ApplicationError> failure ->
                    ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }
}
