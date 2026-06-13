package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import pe.greenminds.ecomind_backend.quests.domain.model.valueobjects.ActivityType;

@Schema(
        name = "ActivityResponse",
        description = "Activity information response"
)
public record ActivityResource(
        @Schema(description = "Activity unique identifier") Long id,
        @Schema(description = "Quest where the activity exists", example = "1") Long questId,
        @Schema(description = "Activity instructions", example = "Check the rooms") String description,
        @Schema(description = "Position order of the activity", example = "1") Integer position,
        @Schema(description = "Type of activity", example = "CHECKBOX") ActivityType type,
        @Schema(description = "Image url for individual activity") String image
) {
}
