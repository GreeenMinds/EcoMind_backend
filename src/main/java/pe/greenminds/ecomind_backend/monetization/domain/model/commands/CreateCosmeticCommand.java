package pe.greenminds.ecomind_backend.monetization.domain.model.commands;

import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.CosmeticType;

public record CreateCosmeticCommand(
        String name,
        String description,
        Integer price,
        CosmeticType type,
        String imageUrl
) {
}
