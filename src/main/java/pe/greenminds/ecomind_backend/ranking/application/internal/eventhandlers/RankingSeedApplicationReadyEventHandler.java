package pe.greenminds.ecomind_backend.ranking.application.internal.eventhandlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.UserCosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.UserCosmeticRepository;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.Family;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.FamilyUser;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.profile.domain.model.valueobjects.FamilyRole;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.RankingEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.entities.ScoreEntryEntity;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.RankingRepository;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.ScoreEntryRepository;

import java.time.Instant;
import java.time.LocalDateTime;
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
    private final FamilyRepository familyRepository;
    private final FamilyUserRepository familyUserRepository;
    private final CosmeticRepository cosmeticRepository;
    private final UserCosmeticRepository userCosmeticRepository;
    private final boolean enabled;
    private final String iamSeedEmail;

    public RankingSeedApplicationReadyEventHandler(
            RankingRepository rankingRepository,
            ScoreEntryRepository scoreEntryRepository,
            UserRepository userRepository,
            FamilyRepository familyRepository,
            FamilyUserRepository familyUserRepository,
            CosmeticRepository cosmeticRepository,
            UserCosmeticRepository userCosmeticRepository,
            @Value("${ranking.seed.enabled:false}") boolean enabled,
            @Value("${iam.seed.email}") String iamSeedEmail) {
        this.rankingRepository = rankingRepository;
        this.scoreEntryRepository = scoreEntryRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
        this.familyUserRepository = familyUserRepository;
        this.cosmeticRepository = cosmeticRepository;
        this.userCosmeticRepository = userCosmeticRepository;
        this.enabled = enabled;
        this.iamSeedEmail = iamSeedEmail;
    }

    @EventListener
    @Order(3)
    public void on(ApplicationReadyEvent event) {
        if (!enabled) return;

        seedRankings();
        seedUsers();
        seedFamilies();
        seedAvatars();
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

    private void seedFamilies() {
        var family = familyRepository.findAll().stream()
                .filter(f -> "Familia Verde".equals(f.getName()))
                .findFirst()
                .orElseGet(() -> {
                    var saved = familyRepository.save(new Family(null, "Familia Verde", "eco-friendly"));
                    System.out.println("Ranking seed: created family 'Familia Verde' with id " + saved.getId());
                    return saved;
                });

        record FamilyMember(String email, FamilyRole role) {}

        for (var m : List.of(
                new FamilyMember("carla.verde@ranking-seed.com", FamilyRole.PARENT),
                new FamilyMember("miguel.rios@ranking-seed.com", FamilyRole.PARENT),
                new FamilyMember("laura.solar@ranking-seed.com", FamilyRole.CHILD),
                new FamilyMember("pedro.bosque@ranking-seed.com", FamilyRole.CHILD),
                new FamilyMember("ana.tierra@ranking-seed.com", FamilyRole.CHILD),
                new FamilyMember(iamSeedEmail, FamilyRole.PARENT)
        )) {
            var user = userRepository.findByEmail(m.email);
            if (user.isEmpty()) {
                System.out.println("Ranking seed: user " + m.email + " not found yet, will retry on next deploy");
                continue;
            }
            if (familyUserRepository.existsByUserIdAndFamilyId(user.get().getId(), family.getId())) continue;
            familyUserRepository.save(new FamilyUser(null, user.get().getId(), family.getId(), m.role, OffsetDateTime.now()));
            System.out.println("Ranking seed: added " + m.email + " as " + m.role + " to 'Familia Verde'");
        }
    }

    private void seedAvatars() {
        record EquipDef(String userEmail, String cosmeticName) {}

        for (var def : List.of(
                new EquipDef("carla.verde@ranking-seed.com", "Mickey Eco Mouse"),
                new EquipDef("miguel.rios@ranking-seed.com", "Superhéroe Verde"),
                new EquipDef("laura.solar@ranking-seed.com", "Gorra del Reciclaje"),
                new EquipDef("pedro.bosque@ranking-seed.com", "Lentes Verdes"),
                new EquipDef("ana.tierra@ranking-seed.com", "Collar de la Tierra")
        )) {
            var userOpt = userRepository.findByEmail(def.userEmail);
            if (userOpt.isEmpty()) continue;

            var cosmeticOpt = cosmeticRepository.findAll().stream()
                    .filter(c -> def.cosmeticName.equals(c.getName()))
                    .findFirst();
            if (cosmeticOpt.isEmpty()) {
                System.out.println("Ranking seed: cosmetic '" + def.cosmeticName + "' not found in store, skipping");
                continue;
            }

            var alreadyEquipped = userCosmeticRepository.findByUserId(userOpt.get().getId()).stream()
                    .anyMatch(UserCosmetic::getEquipped);
            if (alreadyEquipped) continue;

            userCosmeticRepository.save(new UserCosmetic(userOpt.get().getId(), cosmeticOpt.get().getId(), LocalDateTime.now(), true));
            System.out.println("Ranking seed: equipped '" + def.cosmeticName + "' to " + def.userEmail);
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
