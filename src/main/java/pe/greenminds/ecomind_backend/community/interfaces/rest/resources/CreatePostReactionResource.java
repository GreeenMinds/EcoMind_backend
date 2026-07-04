package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "CreatePostReactionRequest",
        description = "Request payload for creating a community post reaction",
        example = """
        {
          "post_id": 1,
          "user_id": 2,
          "reaction_type": "love"
        }
        """
)
public record CreatePostReactionResource(
        @NotNull(message = "is required")
        @JsonProperty("post_id")
        @Schema(description = "Community post identifier", example = "1")
        Long postId,

        @NotNull(message = "is required")
        @JsonProperty("user_id")
        @Schema(description = "User identifier", example = "2")
        Long userId,

        @NotBlank(message = "is required")
        @JsonProperty("reaction_type")
        @Schema(
                description = "Reaction type",
                example = "love",
                allowableValues = {"like", "funny", "love", "surprise", "sad", "angry"}
        )
        String reactionType
) {
}
