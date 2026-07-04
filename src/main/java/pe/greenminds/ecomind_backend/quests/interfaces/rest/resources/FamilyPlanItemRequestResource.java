package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FamilyPlanItemRequestResource(@NotNull @Positive Long questId) {
}
