package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

@Schema(
        name = "CreateActivityRequest",
        description = "Request payload for creating a new activity"
)
public record CreateActivityResource(
        @NotNull @Schema(description = "Quest where the activity exists") Long questId,
        @Schema(description = "Activity instructions") String description,
        @NotNull @Positive @Schema(description = "Position order of the activity") Integer position,
        @NotNull @Schema(description = "Type of activity") ActivityType type,
        @Schema(description = "Image url for individual activity") String image
) {
}
