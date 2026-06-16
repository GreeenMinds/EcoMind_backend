package pe.greenminds.ecomind_backend.profile.interfaces.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.profile.application.commandservices.UserCommandService;
import pe.greenminds.ecomind_backend.profile.application.queryservices.*;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.commands.*;
import pe.greenminds.ecomind_backend.profile.domain.model.queries.*;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.InvitationStatus;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.resources.*;
import pe.greenminds.ecomind_backend.profile.interfaces.rest.transform.*;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ErrorResponseAssembler;
import pe.greenminds.ecomind_backend.shared.interfaces.rest.transform.ResponseEntityAssembler;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserCommandService commandService;
    private final UserQueryService queryService;
    private final FamilyUserQueryService familyUserQueryService;
    private final FamilyQueryService familyQueryService;
    private final FamilyInvitationQueryService invitationQueryService;
    private final FriendQueryService friendQueryService;

    public UserController(UserCommandService commandService, UserQueryService queryService,
                          FamilyUserQueryService familyUserQueryService, FamilyQueryService familyQueryService,
                          FamilyInvitationQueryService invitationQueryService, FriendQueryService friendQueryService) {
        this.commandService = commandService;
        this.queryService = queryService;
        this.familyUserQueryService = familyUserQueryService;
        this.familyQueryService = familyQueryService;
        this.invitationQueryService = invitationQueryService;
        this.friendQueryService = friendQueryService;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        return ResponseEntity.ok(queryService.handle(new GetAllUsersQuery()).stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity).toList());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        var user = queryService.handle(new GetUserByIdQuery(userId));
        if (user.isEmpty()) return ErrorResponseAssembler.toErrorResponseFromApplicationError(ApplicationError.notFound("User", userId.toString()));
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(user.get()));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(CreateUserCommandFromResourceAssembler.toCommandFromResource(resource)),
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(UpdateUserCommandFromResourceAssembler.toCommandFromResource(userId, resource)),
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        var result = commandService.handle(new DeleteUserCommand(userId));
        return switch (result) {
            case Result.Success<User, ApplicationError> ignored -> ResponseEntity.noContent().build();
            case Result.Failure<User, ApplicationError> failure -> ErrorResponseAssembler.toErrorResponseFromApplicationError(failure.error());
        };
    }

    @PatchMapping("/{userId}/commitment")
    public ResponseEntity<?> updateCommitment(@PathVariable Long userId, @RequestBody CommitmentResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new UpdateUserCommitmentCommand(userId, resource.commitment())),
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @PatchMapping("/{userId}/stats")
    public ResponseEntity<?> updateStats(@PathVariable Long userId, @RequestBody UserStatsResource resource) {
        return ResponseEntityAssembler.toResponseEntityFromResult(
                commandService.handle(new UpdateUserStatsCommand(userId, resource.gemBalance(), resource.ecopoints(),
                        resource.streak(), ProfileDateTimeMapper.toLocalDate(resource.lastStreakDate()))),
                UserResourceFromEntityAssembler::toResourceFromEntity,
                HttpStatus.OK);
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<?> getProfile(@PathVariable Long userId) {
        var user = queryService.handle(new GetUserByIdQuery(userId));
        if (user.isEmpty()) return ErrorResponseAssembler.toErrorResponseFromApplicationError(ApplicationError.notFound("User", userId.toString()));

        var familyUsers = familyUserQueryService.handle(new GetFamilyUsersQuery(userId, null));
        var familyMembers = List.<FamilyUserResource>of();
        FamilyResource family = null;
        if (!familyUsers.isEmpty()) {
            var familyId = familyUsers.getFirst().getFamilyId();
            family = familyQueryService.handle(new GetFamilyByIdQuery(familyId))
                    .map(FamilyResourceFromEntityAssembler::toResourceFromEntity)
                    .orElse(null);
            familyMembers = familyUserQueryService.handle(new GetFamilyUsersQuery(null, familyId)).stream()
                    .map(FamilyUserResourceFromEntityAssembler::toResourceFromEntity).toList();
        }

        var received = invitationQueryService.handle(new GetFamilyInvitationsQuery(userId, null, InvitationStatus.PENDING)).stream()
                .map(FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity).toList();
        var sent = invitationQueryService.handle(new GetFamilyInvitationsQuery(null, userId, InvitationStatus.PENDING)).stream()
                .map(FamilyInvitationResourceFromEntityAssembler::toResourceFromEntity).toList();
        var friends = friendQueryService.handle(new GetFriendsQuery(userId, FriendStatus.ACCEPTED)).stream()
                .map(FriendResourceFromEntityAssembler::toResourceFromEntity).toList();

        return ResponseEntity.ok(new ProfileSummaryResource(
                UserResourceFromEntityAssembler.toResourceFromEntity(user.get()),
                family,
                familyMembers,
                received,
                sent,
                friends,
                List.of()
        ));
    }
}
