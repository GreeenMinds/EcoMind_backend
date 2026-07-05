package pe.greenminds.ecomind_backend.community.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "UpdatePostReactionTypeRequest",
        description = "Request payload for updating a community post reaction type",
        example = """
        {
          "reaction_type": "funny"
        }
        """
)
public record UpdatePostReactionTypeResource(
        @NotBlank(message = "is required")
        @JsonProperty("reaction_type")
        @Schema(
                description = "Reaction type",
                example = "funny",
                allowableValues = {"like", "funny", "love", "surprise", "sad", "angry"}
        )
        String reactionType
) {
}
