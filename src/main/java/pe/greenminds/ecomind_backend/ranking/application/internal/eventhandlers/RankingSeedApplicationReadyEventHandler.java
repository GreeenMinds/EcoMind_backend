package pe.greenminds.ecomind_backend.ranking.application.internal.eventhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.RankingRepository;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.ScoreEntryRepository;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class RankingSeedApplicationReadyEventHandler {

    private final RankingRepository rankingRepository;
    private final ScoreEntryRepository scoreEntryRepository;
    private final UserRepository userRepository;
    private final boolean enabled;

    public RankingSeedApplicationReadyEventHandler(
            RankingRepository rankingRepository,
            ScoreEntryRepository scoreEntryRepository,
            UserRepository userRepository,
            @Value("${ranking.seed.enabled:false}") boolean enabled) {
        this.rankingRepository = rankingRepository;
        this.scoreEntryRepository = scoreEntryRepository;
        this.userRepository = userRepository;
        this.enabled = enabled;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        if (!enabled) return;

        seedRankings();
        seedUsers();
        seedScoreEntries();
    }

    private void seedRankings() {
        if (rankingRepository.count() > 0) return;

        rankingRepository.save(new RankingEntity("Global Ranking", RankingType.GLOBAL, new Date(), null, true));
        rankingRepository.save(new RankingEntity("Weekly Ranking", RankingType.WEEKLY, new Date(), null, true));
        rankingRepository.save(new RankingEntity("Monthly Ranking", RankingType.MONTHLY, new Date(), null, true));

        System.out.println("Ranking seed: created 3 ranking definitions");
    }

    private void seedUsers() {
        record SeedUser(String email, String name, int ecopoints) {}

        var seedUsers = List.of(
                new SeedUser("carla.verde@ranking-seed.com", "Carla Verde", 780),
                new SeedUser("miguel.rios@ranking-seed.com", "Miguel Ríos", 450),
                new SeedUser("laura.solar@ranking-seed.com", "Laura Solar", 320),
                new SeedUser("pedro.bosque@ranking-seed.com", "Pedro Bosque", 150),
                new SeedUser("ana.tierra@ranking-seed.com", "Ana Tierra", 50)
        );

        for (var su : seedUsers) {
            if (userRepository.existsByEmail(su.email)) continue;
            var user = new User(null, 1L, su.email, null, su.name, 0, null,
                    OffsetDateTime.now(), 0, su.ecopoints, null);
            userRepository.save(user);
            System.out.println("Ranking seed: created user " + su.name + " with " + su.ecopoints + " ecopoints");
        }
    }

    private void seedScoreEntries() {
        if (scoreEntryRepository.count() > 0) return;

        var users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("Ranking seed: no users found, skipping score entries");
            return;
        }

        var rng = new Random(42);
        var now = Instant.now();
        int totalEntries = 0;
        Map<Long, Integer> userEcopoints = new HashMap<>();

        for (var user : users) {
            var userId = user.getId();
            var ecopoints = user.getEcopoints() == null ? 100 : user.getEcopoints();
            var base = Math.max(ecopoints / 5, 20);
            int userTotal = 0;

            // Recent entries (last 1-3 days) → affect WEEKLY
            int recentCount = rng.nextInt(2) + 2;
            for (int i = 0; i < recentCount; i++) {
                var daysAgo = rng.nextInt(3) + 1;
                var hoursAgo = rng.nextInt(12);
                var date = Date.from(now.minus(daysAgo, ChronoUnit.DAYS).minus(hoursAgo, ChronoUnit.HOURS));
                var score = rng.nextInt(base / 2) + 10;
                scoreEntryRepository.save(new ScoreEntryEntity(userId, score, "QUEST_COMPLETION", "Completed daily quest", date));
                userTotal += score;
                totalEntries++;
            }

            // Medium entries (10-20 days ago) → affect MONTHLY but not WEEKLY
            int mediumCount = rng.nextInt(2) + 1;
            for (int i = 0; i < mediumCount; i++) {
                var daysAgo = rng.nextInt(11) + 10;
                var date = Date.from(now.minus(daysAgo, ChronoUnit.DAYS));
                var score = rng.nextInt(base) + 20;
                scoreEntryRepository.save(new ScoreEntryEntity(userId, score, "QUEST_COMPLETION", "Completed weekly challenge", date));
                userTotal += score;
                totalEntries++;
            }

            // Old entries (35-60 days ago) → only affect GLOBAL
            int oldCount = rng.nextInt(2) + 1;
            for (int i = 0; i < oldCount; i++) {
                var daysAgo = rng.nextInt(26) + 35;
                var date = Date.from(now.minus(daysAgo, ChronoUnit.DAYS));
                var score = rng.nextInt(base * 2) + 50;
                scoreEntryRepository.save(new ScoreEntryEntity(userId, score, "ACHIEVEMENT", "Achievement unlocked", date));
                userTotal += score;
                totalEntries++;
            }

            userEcopoints.put(userId, userTotal);
        }

        // Update each user's ecopoints so GLOBAL ranking reflects the seeded data
        for (var user : users) {
            var total = userEcopoints.get(user.getId());
            user.updateStats(null, total, null, null);
            userRepository.save(user);
        }

        System.out.println("Ranking seed: created " + totalEntries + " score entries for " + users.size() + " users and updated their ecopoints");
    }
}
