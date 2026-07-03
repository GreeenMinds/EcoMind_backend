package pe.greenminds.ecomind_backend.quests.application.queryservices;

public record CollabQuestPermissions(
        boolean canInvite,
        boolean canStart,
        boolean canAcceptInvitation,
        boolean canLeave,
        boolean canRemoveMembers,
        boolean canDeleteSession
) {
}
