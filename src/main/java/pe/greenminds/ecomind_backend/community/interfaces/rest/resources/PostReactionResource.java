package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "PostReactionResponse",
        description = "Community post reaction information response",
        example = """
        {
          "id": 1,
          "post_id": 1,
          "user_id": 2,
          "reaction_type": "love",
          "created_at": "2026-07-04T09:30:00"
        }
        """
)
public record PostReactionResource(
        @Schema(description = "Post reaction unique identifier", example = "1")
        Long id,

        @JsonProperty("post_id")
        @Schema(description = "Community post identifier", example = "1")
        Long postId,

        @JsonProperty("user_id")
        @Schema(description = "User identifier", example = "2")
        Long userId,

        @JsonProperty("reaction_type")
        @Schema(
                description = "Reaction type",
                example = "love",
                allowableValues = {"like", "funny", "love", "surprise", "sad", "angry"}
        )
        String reactionType,

        @JsonProperty("created_at")
        @Schema(description = "Reaction creation date", example = "2026-07-04T09:30:00")
        LocalDateTime createdAt
) {
}
