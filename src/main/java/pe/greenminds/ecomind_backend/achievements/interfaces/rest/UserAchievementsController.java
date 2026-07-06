package pe.greenminds.ecomind_backend.achievements.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.achievements.application.internal.services.UserAchievementEvaluationService;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources.UserAchievementResource;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.transform.UserAchievementResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/user-achievements", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User Achievements", description = "User Achievement Query Endpoints")
public class UserAchievementsController {
    private final UserAchievementEvaluationService userAchievementEvaluationService;
    private final AchievementRepository achievementRepository;

    public UserAchievementsController(
            UserAchievementEvaluationService userAchievementEvaluationService,
            AchievementRepository achievementRepository
    ) {
        this.userAchievementEvaluationService = userAchievementEvaluationService;
        this.achievementRepository = achievementRepository;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserAchievementResource>> getUserAchievements(
            @PathVariable Long userId
    ) {
        var userAchievements = userAchievementEvaluationService.evaluateAndUnlock(userId);
        var resources = userAchievements.stream()
                .map(entity -> UserAchievementResourceFromEntityAssembler.toResourceFromEntity(
                        entity,
                        achievementRepository.findById(entity.getAchievementId()).orElse(null)
                ))
                .toList();
        userAchievementEvaluationService.markAchievementsAsSeen(userAchievements);
        return ResponseEntity.ok(resources);
    }
}
