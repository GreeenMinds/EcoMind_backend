package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.MultiplierPersistenceEntity;

public class MultiplierPersistenceAssembler {

    private MultiplierPersistenceAssembler() {}

    public static Multiplier toDomainFromPersistence(MultiplierPersistenceEntity entity) {
        var multiplier = new Multiplier(
                entity.getMultiplierFactor(),
                entity.getDurationMinutes(),
                entity.getGemCost()
        );
        multiplier.setId(entity.getId());
        return multiplier;
    }

    public static MultiplierPersistenceEntity toPersistenceFromDomain(Multiplier multiplier) {
        var entity = new MultiplierPersistenceEntity();
        entity.setId(multiplier.getId());
        entity.setMultiplierFactor(multiplier.getMultiplierFactor());
        entity.setDurationMinutes(multiplier.getDurationMinutes());
        entity.setGemCost(multiplier.getGemCost());
        return entity;
    }
}
