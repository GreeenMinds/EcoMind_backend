package pe.greenminds.ecomind_backend.quests.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.quests.application.queryservices.CollabQuestSessionState;
import pe.greenminds.ecomind_backend.quests.interfaces.rest.resources.CollabQuestSessionStateResource;

import java.util.List;

public final class CollabQuestSessionStateResourceAssembler {
    private CollabQuestSessionStateResourceAssembler() {
    }

    public static CollabQuestSessionStateResource toResource(
            CollabQuestSessionState state
    ) {
        var questUserResource = state.questUser() == null
                ? null
                : QuestUserResourceFromEntityAssembler.toResourceFromEntity(
                        state.questUser()
                );
        var activityUserResources = state.activityUsers()
                .stream()
                .map(ActivityUserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return new CollabQuestSessionStateResource(
                CollabQuestSessionResourceFromEntityAssembler.toResourceFromEntity(
                        state.session()
                ),
                List.of(),
                questUserResource,
                activityUserResources
        );
    }
}
