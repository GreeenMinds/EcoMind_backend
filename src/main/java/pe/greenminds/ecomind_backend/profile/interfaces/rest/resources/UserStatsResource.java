package pe.greenminds.ecomind_backend.profile.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = """
        {
          "gem_balance": 1000,
          "ecopoints": 0,
          "streak": 0,
          "last_streak_date": null
        }
        """)
public record UserStatsResource(
        @JsonProperty("gem_balance")
        @Schema(nullable = true, example = "1000")
        Integer gemBalance,
        @Schema(nullable = true, example = "0")
        Integer ecopoints,
        @Schema(nullable = true, example = "0")
        Integer streak,
        @JsonProperty("last_streak_date")
        @Schema(nullable = true, example = "null", description = "Last streak date in yyyy-MM-dd format, or null.")
        String lastStreakDate
) {
}
