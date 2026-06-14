package pe.greenminds.ecomind_backend.monetization.domain.model.events;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;

import java.math.BigDecimal;

public record MultiplierCreatedEvent(
        Long multiplierId,
        BigDecimal multiplierFactor,
        Integer durationMinutes,
        Integer gemCost
) {
    public static MultiplierCreatedEvent from(Multiplier multiplier) {
        return new MultiplierCreatedEvent(
                multiplier.getId(),
                multiplier.getMultiplierFactor(),
                multiplier.getDurationMinutes(),
                multiplier.getGemCost()
        );
    }
}
