package pe.greenminds.ecomind_backend.learning.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "CompleteTutorialStepRequest",
        description = "Request payload for completing a tutorial step",
        example = """
                {
                  "userId": 1
                }
                """
)
public record CompleteTutorialStepResource(
        @Schema(description = "User identifier", example = "1")
        Long userId
) {
}
