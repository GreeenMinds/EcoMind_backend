package pe.greenminds.ecomind_backend.ranking.application.internal.services;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.ranking.application.outboundservices.external.MonetizationRankingExternalService;
import pe.greenminds.ecomind_backend.ranking.application.outboundservices.external.ProfileRankingExternalService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.ScoreEntryRepository;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.LeaderboardEntryResource;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final ProfileRankingExternalService profileService;
    private final MonetizationRankingExternalService monetizationService;
    private final ScoreEntryRepository scoreEntryRepository;

    public LeaderboardService(ProfileRankingExternalService profileService,
                              MonetizationRankingExternalService monetizationService,
                              ScoreEntryRepository scoreEntryRepository) {
        this.profileService = profileService;
        this.monetizationService = monetizationService;
        this.scoreEntryRepository = scoreEntryRepository;
    }

    public List<LeaderboardEntryResource> getLeaderboard(String type, Long currentUserId) {
        var users = profileService.fetchAllUsers();

        Map<Long, Integer> scoresByUser = switch (type.toUpperCase()) {
            case "GLOBAL" -> computeGlobalScores(users);
            case "WEEKLY" -> computePeriodScores(users, 7);
            case "MONTHLY" -> computePeriodScores(users, 30);
            default -> throw new IllegalArgumentException("Invalid ranking type: " + type);
        };

        var sortedUsers = scoresByUser.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .toList();

        List<LeaderboardEntryResource> entries = new ArrayList<>();
        int position = 1;
        for (var entry : sortedUsers) {
            var user = users.stream()
                    .filter(u -> u.getId().equals(entry.getKey()))
                    .findFirst()
                    .orElse(null);
            if (user == null) continue;

            var cosmetics = monetizationService.fetchEquippedCosmetics(user.getId());

            entries.add(new LeaderboardEntryResource(
                    user.getId(),
                    user.getName(),
                    entry.getValue(),
                    position,
                    cosmetics.avatarUrl(),
                    cosmetics.overlayUrl(),
                    cosmetics.overlayType(),
                    currentUserId != null && currentUserId.equals(user.getId())
            ));
            position++;
        }

        return entries;
    }

    private Map<Long, Integer> computeGlobalScores(List<User> users) {
        return users.stream()
                .collect(Collectors.toMap(
                        User::getId,
                        u -> u.getEcopoints() == null ? 0 : u.getEcopoints()
                ));
    }

    private Map<Long, Integer> computePeriodScores(List<User> users, int days) {
        var cutoff = Date.from(java.time.Instant.now().minus(days, ChronoUnit.DAYS));
        var allEntries = scoreEntryRepository.findByEntryDateAfter(cutoff);

        Map<Long, Integer> scores = new HashMap<>();
        for (var entry : allEntries) {
            scores.merge(entry.getUserId(), entry.getScore(), Integer::sum);
        }

        for (var user : users) {
            scores.putIfAbsent(user.getId(), 0);
        }

        return scores;
    }
}
