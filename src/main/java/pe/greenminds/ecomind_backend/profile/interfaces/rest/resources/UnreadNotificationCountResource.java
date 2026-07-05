package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UnreadNotificationCountResource(
        @JsonProperty("user_id") Long userId,
        @JsonProperty("unread_count") long unreadCount
) {
}
