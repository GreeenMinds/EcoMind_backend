package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(example = """
        {
          "community_id": 1,
          "email": "user@example.com",
          "birth_date": "2000-02-20",
          "name": "Eco User",
          "streak": 0,
          "commitment": "Reduce plastic use",
          "registered_at": null,
          "gem_balance": 1000,
          "ecopoints": 0,
          "last_streak_date": null
        }
        """)
public record UpdateUserResource(
        @JsonProperty("community_id")
        @Schema(description = "Community identifier assigned to the user.", example = "1")
        Long communityId,
        @Email @NotBlank String email,
        @JsonProperty("birth_date") String birthDate,
        @NotBlank String name,
        Integer streak,
        String commitment,
        @JsonProperty("registered_at") String registeredAt,
        @JsonProperty("gem_balance") Integer gemBalance,
        Integer ecopoints,
        @JsonProperty("last_streak_date")
        @Schema(nullable = true, example = "null")
        String lastStreakDate
) {
}
