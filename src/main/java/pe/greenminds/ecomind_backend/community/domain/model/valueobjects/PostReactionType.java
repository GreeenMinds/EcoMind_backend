package pe.greenminds.ecomind_backend.community.domain.model.valueobjects;

import java.util.Arrays;

public enum PostReactionType {
    LIKE("like"),
    FUNNY("funny"),
    LOVE("love"),
    SURPRISE("surprise"),
    SAD("sad"),
    ANGRY("angry");

    private final String value;

    PostReactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PostReactionType fromValue(String value) {
        return Arrays.stream(values())
                .filter(type -> type.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid post reaction type: " + value));
    }
}
