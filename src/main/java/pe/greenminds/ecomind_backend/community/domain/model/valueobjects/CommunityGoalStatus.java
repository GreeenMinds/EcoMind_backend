package pe.greenminds.ecomind_backend.community.domain.model.valueobjects;

import java.util.Arrays;

public enum CommunityGoalStatus {
    ACTIVE("active"),
    COMPLETED("completed");

    private final String value;

    CommunityGoalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static CommunityGoalStatus fromValue(String value) {
        return Arrays.stream(values())
                .filter(status -> status.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid community goal status: " + value));
    }
}
