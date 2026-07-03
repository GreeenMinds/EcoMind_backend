package pe.greenminds.ecomind_backend.quests.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

@Schema(
        name = "CreateMinigameRequest",
        example = """
        {
          "name": "Recicla rapido",
          "description": "Clasifica residuos correctamente.",
          "url": "https://example.com/minigames/recycle",
          "completionRules": {
            "minScore": 800
          }
        }
        """
)
public record CreateMinigameResource(
        @NotBlank
        String name,

        String description,

        @NotBlank
        String url,

        @NotNull
        Map<String, Object> completionRules
) {
}
