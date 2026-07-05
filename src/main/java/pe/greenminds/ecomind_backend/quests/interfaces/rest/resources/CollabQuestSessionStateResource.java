package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSource;

import java.util.List;

public record CollabQuestSessionStateResource(
        CollabQuestSessionResource session,
        List<CollabQuestMemberResource> members,
        CollabQuestMemberResource currentMember,
        CollabQuestMemberResource pendingInvitation,
        CollabQuestPermissionsResource permissions,
        CollabQuestCountersResource counters,
        List<Long> unavailableUserIds,
        CollabQuestSource source,
        Long familyPlanId,
        Long familyPlanItemId
) {
}
