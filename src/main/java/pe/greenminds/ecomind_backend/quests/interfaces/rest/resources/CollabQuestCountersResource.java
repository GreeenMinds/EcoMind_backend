package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

public record CollabQuestCountersResource(
        int acceptedInvites,
        int pendingInvites,
        int activeInvites,
        int maxInvites
) {
}
