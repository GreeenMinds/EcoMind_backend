package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationResource(
        Long id,
        @JsonProperty("user_id") Long userId,
        String type,
        String title,
        String message,
        @JsonProperty("is_read") boolean read,
        @JsonProperty("reference_type") String referenceType,
        @JsonProperty("reference_id") Long referenceId,
        @JsonProperty("created_at") String createdAt,
        @JsonProperty("read_at") String readAt
) {
}
