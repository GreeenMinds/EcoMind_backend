package pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

public record CreateAchievementResource(
        @NotBlank String name,
        String description,
        @NotBlank String type,
        Integer thresholdValue
) {}
