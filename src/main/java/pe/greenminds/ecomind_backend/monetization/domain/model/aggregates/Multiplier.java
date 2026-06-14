package pe.greenminds.ecomind_backend.monetization.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import pe.greenminds.ecomind_backend.monetization.domain.model.events.MultiplierCreatedEvent;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.math.BigDecimal;
import java.util.Objects;

public class Multiplier extends AbstractDomainAggregateRoot<Multiplier> {

    @Getter
    @Setter
    private Long id;

    private BigDecimal multiplierFactor;
    private Integer durationMinutes;
    private Integer gemCost;

    public Multiplier(Long id, BigDecimal multiplierFactor, Integer durationMinutes, Integer gemCost) {
        this.id = id;
        this.multiplierFactor = Objects.requireNonNull(multiplierFactor, "multiplierFactor must not be null");
        this.durationMinutes = Objects.requireNonNull(durationMinutes, "durationMinutes must not be null");
        this.gemCost = Objects.requireNonNull(gemCost, "gemCost must not be null");
    }

    public Multiplier(BigDecimal multiplierFactor, Integer durationMinutes, Integer gemCost) {
        this(null, multiplierFactor, durationMinutes, gemCost);
    }

    public BigDecimal getMultiplierFactor() { return multiplierFactor; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public Integer getGemCost() { return gemCost; }

    public void onCreated() {
        registerDomainEvent(MultiplierCreatedEvent.from(this));
    }
}
