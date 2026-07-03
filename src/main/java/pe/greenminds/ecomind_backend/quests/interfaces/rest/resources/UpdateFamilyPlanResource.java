package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record UpdateFamilyPlanResource(
        @Valid
        @NotEmpty
        List<FamilyPlanItemRequestResource> items
) {
}
