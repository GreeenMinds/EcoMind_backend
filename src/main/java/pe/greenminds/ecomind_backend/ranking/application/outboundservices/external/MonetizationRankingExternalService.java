package pe.greenminds.ecomind_backend.ranking.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MonetizationRankingExternalService {

    private final UserCosmeticRepository userCosmeticRepository;
    private final CosmeticRepository cosmeticRepository;

    public MonetizationRankingExternalService(
            UserCosmeticRepository userCosmeticRepository,
            CosmeticRepository cosmeticRepository) {
        this.userCosmeticRepository = userCosmeticRepository;
        this.cosmeticRepository = cosmeticRepository;
    }

    public record EquippedCosmetics(String avatarUrl, String overlayUrl, String overlayType) {}

    public EquippedCosmetics fetchEquippedCosmetics(Long userId) {
        var equipped = userCosmeticRepository.findByUserId(userId).stream()
                .filter(UserCosmetic::getEquipped)
                .toList();

        String avatarUrl = null;
        String overlayUrl = null;
        String overlayType = null;

        var allCosmetics = cosmeticRepository.findAll();
        var cosmeticMap = allCosmetics.stream()
                .collect(Collectors.toMap(Cosmetic::getId, c -> c));

        for (var uc : equipped) {
            var cosmetic = cosmeticMap.get(uc.getCosmeticId());
            if (cosmetic == null) continue;
            switch (cosmetic.getType()) {
                case AVATAR -> avatarUrl = cosmetic.getImageUrl();
                default -> {
                    overlayUrl = cosmetic.getImageUrl();
                    overlayType = cosmetic.getType().name().toLowerCase();
                }
            }
        }

        return new EquippedCosmetics(avatarUrl, overlayUrl, overlayType);
    }
}
