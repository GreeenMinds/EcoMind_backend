package pe.greenminds.ecomind_backend.monetization.application.internal.eventhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.CosmeticType;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;

import java.util.List;

@Component
public class MonetizationSeedApplicationReadyEventHandler {

    private final CosmeticRepository cosmeticRepository;
    private final UserCosmeticRepository userCosmeticRepository;
    private final boolean enabled;

    public MonetizationSeedApplicationReadyEventHandler(
            CosmeticRepository cosmeticRepository,
            UserCosmeticRepository userCosmeticRepository,
            @Value("${monetization.seed.enabled:false}") boolean enabled) {
        this.cosmeticRepository = cosmeticRepository;
        this.userCosmeticRepository = userCosmeticRepository;
        this.enabled = enabled;
    }

    @EventListener
    @Order(2)
    public void on(ApplicationReadyEvent event) {
        if (!enabled) return;

        cleanupOldCustomCosmetics();

        if (!cosmeticRepository.findAll().isEmpty()) return;

        record CosmeticDef(String name, String description, int price, CosmeticType type, String imageUrl) {}

        List<CosmeticDef> items = List.of(
                new CosmeticDef("Mickey Eco Mouse",
                        "Avatar divertido estilo ratón ecológico",
                        200, CosmeticType.AVATAR,
                        "https://images.unsplash.com/photo-1566576912321-d58ddd7a6088?w=200&h=200&fit=crop"),
                new CosmeticDef("Superhéroe Verde",
                        "Disfraz de superhéroe del medio ambiente",
                        300, CosmeticType.AVATAR,
                        "https://images.unsplash.com/photo-1618517354310-0cb7b660f242?w=200&h=200&fit=crop"),
                new CosmeticDef("Gorra del Reciclaje",
                        "Gorra con símbolo de reciclaje bordado",
                        80, CosmeticType.HAT,
                        "https://images.unsplash.com/photo-1534215754734-18e55d13e346?w=200&h=200&fit=crop"),
                new CosmeticDef("Sombrero Solar",
                        "Sombrero para protegerte del sol mientras cuidas el planeta",
                        100, CosmeticType.HAT,
                        "https://images.unsplash.com/photo-1521486363258-9ce59d453418?w=200&h=200&fit=crop"),
                new CosmeticDef("Lentes Verdes",
                        "Lentes ecológicos con marco de bambú",
                        120, CosmeticType.GLASSES,
                        "https://images.unsplash.com/photo-1574258495973-f010dfbb5371?w=200&h=200&fit=crop"),
                new CosmeticDef("Gafas Eco-Friendly",
                        "Gafas de sol con filtro UV y materiales reciclados",
                        140, CosmeticType.GLASSES,
                        "https://images.unsplash.com/photo-1591076482161-42ce6da69f67?w=200&h=200&fit=crop"),
                new CosmeticDef("Collar de la Tierra",
                        "Collar con dije del planeta hecho de materiales reciclados",
                        160, CosmeticType.NECKLACE,
                        "https://images.unsplash.com/photo-1599643478518-a784e5dc4c8f?w=200&h=200&fit=crop"),
                new CosmeticDef("Moño Natural",
                        "Moño decorativo con flores secas y tela orgánica",
                        90, CosmeticType.BUN,
                        "https://images.unsplash.com/photo-1605980776566-48b7625083b5?w=200&h=200&fit=crop")
        );

        for (var item : items) {
            cosmeticRepository.save(new Cosmetic(item.name, item.description, item.price, item.type, item.imageUrl));
            System.out.println("Monetization seed: created cosmetic '" + item.name + "' (" + item.type + ") - $" + item.price);
        }
    }

    private void cleanupOldCustomCosmetics() {
        List<String> oldNames = List.of(
                "Avatar Naturaleza", "Avatar Océano", "Avatar Sol",
                "Avatar Bosque", "Avatar Tierra"
        );

        var allUserCosmetics = userCosmeticRepository.findAll();

        for (var name : oldNames) {
            var optional = cosmeticRepository.findAll().stream()
                    .filter(c -> c.getName().equals(name))
                    .findFirst();
            if (optional.isEmpty()) continue;

            var cosmetic = optional.get();
            allUserCosmetics.stream()
                    .filter(uc -> uc.getCosmeticId().equals(cosmetic.getId()))
                    .forEach(uc -> userCosmeticRepository.deleteById(uc.getId()));

            cosmeticRepository.deleteById(cosmetic.getId());
            System.out.println("Monetization seed: deleted old cosmetic '" + name + "' from store and user assignments");
        }
    }
}
