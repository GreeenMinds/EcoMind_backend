package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import java.math.BigDecimal;

public record CreateMultiplierCommand(
        BigDecimal multiplierFactor,
        Integer durationMinutes,
        Integer gemCost
) {
}
