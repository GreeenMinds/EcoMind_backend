package pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.assemblers;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.infrastructure.persistence.jpa.entities.CosmeticPersistenceEntity;

public class CosmeticPersistenceAssembler {

    private CosmeticPersistenceAssembler() {}

    public static Cosmetic toDomainFromPersistence(CosmeticPersistenceEntity entity) {
        var cosmetic = new Cosmetic(
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getType(),
                entity.getImageUrl()
        );
        cosmetic.setId(entity.getId());
        return cosmetic;
    }

    public static CosmeticPersistenceEntity toPersistenceFromDomain(Cosmetic cosmetic) {
        var entity = new CosmeticPersistenceEntity();
        entity.setId(cosmetic.getId());
        entity.setName(cosmetic.getName());
        entity.setDescription(cosmetic.getDescription());
        entity.setPrice(cosmetic.getPrice());
        entity.setType(cosmetic.getType());
        entity.setImageUrl(cosmetic.getImageUrl());
        return entity;
    }
}
