package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

public record CollabQuestPermissionsResource(
        boolean canInvite,
        boolean canStart,
        boolean canAcceptInvitation,
        boolean canLeave,
        boolean canRemoveMembers,
        boolean canDeleteSession
) {
}
