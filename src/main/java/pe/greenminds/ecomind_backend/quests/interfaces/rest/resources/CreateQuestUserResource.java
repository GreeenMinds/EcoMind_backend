package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(
        name = "CreateQuestUserRequest",
        description = "Request payload for assigning a quest to a user",
        example = """
        {
          "userId": 1,
          "questId": 1,
          "collaborativeSessionId": null
        }
        """
)
public record CreateQuestUserResource(
        @NotNull
        @Positive
        @Schema(description = "User identifier", example = "1")
        Long userId,

        @NotNull
        @Positive
        @Schema(description = "Quest identifier", example = "1")
        Long questId,

        @Positive
        @Schema(description = "Optional collaborative session identifier", example = "1")
        Long collaborativeSessionId
) {
}
