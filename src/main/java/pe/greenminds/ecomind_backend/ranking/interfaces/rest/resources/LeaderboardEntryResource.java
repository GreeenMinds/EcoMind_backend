package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record LeaderboardEntryResource(
    Long userId,
    String username,
    int score,
    int position,
    String equippedAvatarUrl,
    String equippedOverlayUrl,
    String equippedOverlayType,
    boolean isCurrentUser
) {}
