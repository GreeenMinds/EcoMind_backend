package pe.greenminds.ecomind_backend.quests.application.internal.services;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.GemMovement;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementOrigin;
import pe.greenminds.ecomind_backend.monetization.domain.model.valueobjects.MovementType;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.GemMovementRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.time.LocalDate;

@Service
public class QuestRewardService {
    private final UserRepository userRepository;
    private final GemMovementRepository gemMovementRepository;

    public QuestRewardService(
            UserRepository userRepository,
            GemMovementRepository gemMovementRepository
    ) {
        this.userRepository = userRepository;
        this.gemMovementRepository = gemMovementRepository;
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

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User %d not found for rewards".formatted(userId)
                ));

        var today = LocalDate.now();
        var shouldIncreaseStreak = user.getLastStreakDate() == null
                || user.getLastStreakDate().isBefore(today);

        user.updateStats(
                user.getGemBalance() + normalizedGems,
                user.getEcopoints() + normalizedEcopoints,
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
}
