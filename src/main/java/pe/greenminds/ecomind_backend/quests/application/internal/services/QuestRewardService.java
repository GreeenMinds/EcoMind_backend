package pe.greenminds.ecomind_backend.quests.application.internal.services;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.MultiplierRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserMultiplierRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class QuestRewardService {
    private final UserRepository userRepository;
    private final GemMovementRepository gemMovementRepository;
    private final UserMultiplierRepository userMultiplierRepository;
    private final MultiplierRepository multiplierRepository;

    public QuestRewardService(
            UserRepository userRepository,
            GemMovementRepository gemMovementRepository,
            UserMultiplierRepository userMultiplierRepository,
            MultiplierRepository multiplierRepository
    ) {
        this.userRepository = userRepository;
        this.gemMovementRepository = gemMovementRepository;
        this.userMultiplierRepository = userMultiplierRepository;
        this.multiplierRepository = multiplierRepository;
    }

    public void grantRewards(
            Long userId,
            Integer gems,
            Integer ecopoints,
            MovementOrigin origin,
            Long originId
    ) {
        var normalizedGems = gems == null ? 0 : gems;
        var normalizedEcopoints = ecopoints == null ? 0 : ecopoints;
        var ecopointsFactor = activeEcopointsMultiplier(userId);
        var finalEcopoints = (int) Math.round(normalizedEcopoints * ecopointsFactor.doubleValue());

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User %d not found for rewards".formatted(userId)
                ));

        var today = LocalDate.now();
        var shouldIncreaseStreak = user.getLastStreakDate() == null
                || user.getLastStreakDate().isBefore(today);

        user.updateStats(
                user.getGemBalance() + normalizedGems,
                user.getEcopoints() + finalEcopoints,
                shouldIncreaseStreak ? user.getStreak() + 1 : null,
                shouldIncreaseStreak ? today : null
        );
        userRepository.save(user);

        if (normalizedGems > 0) {
            gemMovementRepository.save(
                    new GemMovement(
                            userId,
                            MovementType.QUEST_REWARD,
                            normalizedGems,
                            origin,
                            originId
                    )
            );
        }
    }

    private BigDecimal activeEcopointsMultiplier(Long userId) {
        var now = LocalDateTime.now();
        return userMultiplierRepository.findByUserId(userId).stream()
                .filter(um -> um.getStartDate() != null && um.getEndDate() != null)
                .filter(um -> !now.isBefore(um.getStartDate()) && now.isBefore(um.getEndDate()))
                .map(um -> multiplierRepository.findById(um.getMultiplierId()))
                .flatMap(java.util.Optional::stream)
                .map(Multiplier::getMultiplierFactor)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ONE);
    }
}
