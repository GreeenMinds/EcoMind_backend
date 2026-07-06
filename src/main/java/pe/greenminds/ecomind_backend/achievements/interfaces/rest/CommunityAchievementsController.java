package pe.greenminds.ecomind_backend.achievements.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.achievements.application.internal.services.CommunityAchievementEvaluationService;
import pe.greenminds.ecomind_backend.achievements.domain.repositories.AchievementRepository;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.resources.CommunityAchievementResource;
import pe.greenminds.ecomind_backend.achievements.interfaces.rest.transform.CommunityAchievementResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/community-achievements", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Community Achievements", description = "Community Achievement Query Endpoints")
public class CommunityAchievementsController {
    private final CommunityAchievementEvaluationService communityAchievementEvaluationService;
    private final AchievementRepository achievementRepository;

    public CommunityAchievementsController(
            CommunityAchievementEvaluationService communityAchievementEvaluationService,
            AchievementRepository achievementRepository
    ) {
        this.communityAchievementEvaluationService = communityAchievementEvaluationService;
        this.achievementRepository = achievementRepository;
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<List<CommunityAchievementResource>> getCommunityAchievements(
            @PathVariable Long communityId
    ) {
        var communityAchievements = communityAchievementEvaluationService.evaluateAndUnlock(
                communityId
        );
        var resources = communityAchievements.stream()
                .map(entity -> CommunityAchievementResourceFromEntityAssembler.toResourceFromEntity(
                        entity,
                        achievementRepository.findById(entity.getAchievementId()).orElse(null)
                ))
                .toList();
        communityAchievementEvaluationService.markAchievementsAsSeen(communityAchievements);
        return ResponseEntity.ok(resources);
    }
}
