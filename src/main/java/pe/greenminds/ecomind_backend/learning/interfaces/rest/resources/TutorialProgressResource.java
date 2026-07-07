package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "TutorialProgressResponse",
        description = "Tutorial progress information response",
        example = """
                {
                  "id": 1,
                  "userId": 1,
                  "currentStep": 3,
                  "totalSteps": 5,
                  "completed": false,
                  "skipped": false,
                  "completedAt": null
                }
                """
)
public record TutorialProgressResource(
        @Schema(description = "Tutorial progress unique identifier", example = "1")
        Long id,

        @Schema(description = "User identifier", example = "1")
        Long userId,

        @Schema(description = "Current step in the tutorial", example = "3")
        Integer currentStep,

        @Schema(description = "Total steps in the tutorial", example = "5")
        Integer totalSteps,

        @Schema(description = "Whether the tutorial is completed", example = "false")
        Boolean completed,

        @Schema(description = "Whether the tutorial was skipped", example = "false")
        Boolean skipped,

        @Schema(description = "Timestamp when the tutorial was completed", example = "null")
        String completedAt
) {
}
