package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import pe.greenminds.ecomind_backend.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "multipliers")
public class MultiplierPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "multiplier_factor", nullable = false)
    private BigDecimal multiplierFactor;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "gem_cost", nullable = false)
    private Integer gemCost;

    public BigDecimal getMultiplierFactor() { return multiplierFactor; }
    public void setMultiplierFactor(BigDecimal multiplierFactor) { this.multiplierFactor = multiplierFactor; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public Integer getGemCost() { return gemCost; }
    public void setGemCost(Integer gemCost) { this.gemCost = gemCost; }
}
