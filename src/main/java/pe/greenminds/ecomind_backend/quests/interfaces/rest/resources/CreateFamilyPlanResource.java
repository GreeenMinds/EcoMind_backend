package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateFamilyPlanResource(
        @NotNull
        @Positive
        Long familyId,

        @NotNull
        @Positive
        Long ownerUserId,

        @Valid
        @NotEmpty
        List<FamilyPlanItemRequestResource> items
) {
}
