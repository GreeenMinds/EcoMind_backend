package pe.greenminds.ecomind_backend.quests.application.queryservices;

public record CollabQuestCounters(
        int acceptedInvites,
        int pendingInvites,
        int activeInvites,
        int maxInvites
) {
}
