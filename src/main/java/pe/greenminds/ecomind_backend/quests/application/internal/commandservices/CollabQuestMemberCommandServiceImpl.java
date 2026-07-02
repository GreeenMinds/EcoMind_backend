package pe.greenminds.ecomind_backend.quests.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Friend;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FriendStatus;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FriendRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.quests.application.commandservices.CollabQuestMemberCommandService;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.CollabQuestMember;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.AcceptCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.DeclineCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.commands.InviteCollabQuestMemberCommand;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabMemberStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.CollabQuestStatus;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.MemberRole;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestMemberRepository;
import pe.greenminds.ecomind_backend.quests.domain.repositories.CollabQuestSessionRepository;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

import java.util.Objects;

@Service
public class CollabQuestMemberCommandServiceImpl implements CollabQuestMemberCommandService {
    private final CollabQuestMemberRepository collabQuestMemberRepository;
    private final CollabQuestSessionRepository collabQuestSessionRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FamilyUserRepository familyUserRepository;

    public CollabQuestMemberCommandServiceImpl(
            CollabQuestMemberRepository collabQuestMemberRepository,
            CollabQuestSessionRepository collabQuestSessionRepository,
            UserRepository userRepository,
            FriendRepository friendRepository,
            FamilyUserRepository familyUserRepository
    ) {
        this.collabQuestMemberRepository = collabQuestMemberRepository;
        this.collabQuestSessionRepository = collabQuestSessionRepository;
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
        this.familyUserRepository = familyUserRepository;
    }

    @Override
    public Result<CollabQuestMember, ApplicationError> handle(
            InviteCollabQuestMemberCommand command
    ) {
        var session = collabQuestSessionRepository.findById(command.sessionId());
        if (session.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound("CollabQuestSession", command.sessionId().toString())
            );
        }

        if (session.get().getStatus() != CollabQuestStatus.PENDING) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only pending sessions can receive invitations",
                            "Session %d is %s".formatted(
                                    command.sessionId(),
                                    session.get().getStatus()
                            )
                    )
            );
        }

        if (!userRepository.existsById(command.invitedByUserId())) {
            return Result.failure(
                    ApplicationError.notFound("User", command.invitedByUserId().toString())
            );
        }

        if (!userRepository.existsById(command.invitedUserId())) {
            return Result.failure(
                    ApplicationError.notFound("User", command.invitedUserId().toString())
            );
        }

        if (Objects.equals(command.invitedByUserId(), command.invitedUserId())) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "A user cannot invite themselves",
                            "invitedByUserId and invitedUserId must be different"
                    )
            );
        }

        if (!Objects.equals(session.get().getOwnerId(), command.invitedByUserId())) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only the session owner can invite users",
                            "User %d is not the owner of session %d".formatted(
                                    command.invitedByUserId(),
                                    command.sessionId()
                            )
                    )
            );
        }

        if (!areAcceptedFriends(command.invitedByUserId(), command.invitedUserId())
                && !areFamilyMembers(command.invitedByUserId(), command.invitedUserId())) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Invited user must be a friend or family member",
                            "Users must be accepted friends or belong to the same family"
                    )
            );
        }

        if (collabQuestMemberRepository.existsBySessionIdAndUserId(
                command.sessionId(),
                command.invitedUserId()
        )) {
            return Result.failure(
                    ApplicationError.conflict(
                            "CollabQuestMember",
                            "The user is already invited to this session"
                    )
            );
        }

        var acceptedQuestMemberships = collabQuestMemberRepository.findByUserIdAndQuestId(
                command.invitedUserId(),
                session.get().getQuestId()
        );
        var alreadyAcceptedQuest = acceptedQuestMemberships.stream()
                .anyMatch(member -> member.getStatus() == CollabMemberStatus.ACCEPTED);

        if (alreadyAcceptedQuest) {
            return Result.failure(
                    ApplicationError.conflict(
                            "CollabQuestMember",
                            "The user already accepted an invitation for this quest"
                    )
            );
        }

        try {
            var member = new CollabQuestMember(
                    command.sessionId(),
                    command.invitedUserId(),
                    command.invitedByUserId(),
                    MemberRole.PARTICIPANT,
                    CollabMemberStatus.PENDING
            );

            return Result.success(collabQuestMemberRepository.save(member));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CollabQuestMember", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected(
                            "CollabQuestMember invitation",
                            exception.getMessage()
                    )
            );
        }
    }

    @Override
    public Result<CollabQuestMember, ApplicationError> handle(
            AcceptCollabQuestMemberCommand command
    ) {
        var member = collabQuestMemberRepository.findById(command.memberId());
        if (member == null) {
            return Result.failure(
                    ApplicationError.notFound("CollabQuestMember", command.memberId().toString())
            );
        }

        if (member.getRole() == MemberRole.OWNER) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Owner cannot accept invitations",
                            "Member %d is the session owner".formatted(command.memberId())
                    )
            );
        }

        if (member.getStatus() != CollabMemberStatus.PENDING) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only pending invitations can be accepted",
                            "Member %d is %s".formatted(command.memberId(), member.getStatus())
                    )
            );
        }

        var session = collabQuestSessionRepository.findById(member.getSessionId());
        if (session.isEmpty()) {
            return Result.failure(
                    ApplicationError.notFound(
                            "CollabQuestSession",
                            member.getSessionId().toString()
                    )
            );
        }

        if (session.get().getStatus() != CollabQuestStatus.PENDING) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only pending sessions can accept invitations",
                            "Session %d is %s".formatted(
                                    session.get().getId(),
                                    session.get().getStatus()
                            )
                    )
            );
        }

        var acceptedQuestMemberships = collabQuestMemberRepository.findByUserIdAndQuestId(
                member.getUserId(),
                session.get().getQuestId()
        );
        var alreadyAcceptedQuest = acceptedQuestMemberships.stream()
                .anyMatch(existingMember ->
                        !Objects.equals(existingMember.getId(), member.getId())
                                && existingMember.getStatus() == CollabMemberStatus.ACCEPTED
                );

        if (alreadyAcceptedQuest) {
            return Result.failure(
                    ApplicationError.conflict(
                            "CollabQuestMember",
                            "The user already accepted an invitation for this quest"
                    )
            );
        }

        try {
            member.answerInvite(CollabMemberStatus.ACCEPTED);
            return Result.success(collabQuestMemberRepository.save(member));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CollabQuestMember", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected(
                            "CollabQuestMember accept",
                            exception.getMessage()
                    )
            );
        }
    }

    @Override
    public Result<CollabQuestMember, ApplicationError> handle(
            DeclineCollabQuestMemberCommand command
    ) {
        var member = collabQuestMemberRepository.findById(command.memberId());
        if (member == null) {
            return Result.failure(
                    ApplicationError.notFound("CollabQuestMember", command.memberId().toString())
            );
        }

        if (member.getRole() == MemberRole.OWNER) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Owner cannot decline invitations",
                            "Member %d is the session owner".formatted(command.memberId())
                    )
            );
        }

        if (member.getStatus() != CollabMemberStatus.PENDING) {
            return Result.failure(
                    ApplicationError.businessRuleViolation(
                            "Only pending invitations can be declined",
                            "Member %d is %s".formatted(command.memberId(), member.getStatus())
                    )
            );
        }

        try {
            member.declineInvite();
            return Result.success(collabQuestMemberRepository.save(member));
        } catch (IllegalArgumentException | NullPointerException exception) {
            return Result.failure(
                    ApplicationError.validationError("CollabQuestMember", exception.getMessage())
            );
        } catch (Exception exception) {
            return Result.failure(
                    ApplicationError.unexpected(
                            "CollabQuestMember decline",
                            exception.getMessage()
                    )
            );
        }
    }

    private boolean areAcceptedFriends(Long userId, Long friendId) {
        return friendRepository.search(userId, FriendStatus.ACCEPTED)
                .stream()
                .anyMatch(friend -> isFriendPair(friend, userId, friendId));
    }

    private boolean isFriendPair(Friend friend, Long userId, Long friendId) {
        return Objects.equals(friend.getUserId(), userId)
                && Objects.equals(friend.getFriendId(), friendId)
                || Objects.equals(friend.getUserId(), friendId)
                && Objects.equals(friend.getFriendId(), userId);
    }

    private boolean areFamilyMembers(Long firstUserId, Long secondUserId) {
        var firstUserFamilies = familyUserRepository.findByUserId(firstUserId);
        var secondUserFamilies = familyUserRepository.findByUserId(secondUserId);

        return firstUserFamilies.stream()
                .anyMatch(firstFamily -> secondUserFamilies.stream()
                        .anyMatch(secondFamily -> Objects.equals(
                                firstFamily.getFamilyId(),
                                secondFamily.getFamilyId()
                        )));
    }
}
