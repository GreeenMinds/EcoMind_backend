package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetAllRankingsQuery;
import pe.greenminds.ecomind_backend.ranking.domain.model.queries.GetRankingByIdQuery;
import pe.greenminds.ecomind_backend.ranking.domain.services.RankingCommandService;
import pe.greenminds.ecomind_backend.ranking.domain.services.RankingQueryService;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.CreateRankingResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.RankingResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.CreateRankingCommandFromResourceAssembler;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.RankingResourceFromEntityAssembler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/rankings", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Rankings", description = "Ranking Management Endpoints")
public class RankingsController {

    private final RankingCommandService rankingCommandService;
    private final RankingQueryService rankingQueryService;

    public RankingsController(RankingCommandService rankingCommandService,
                              RankingQueryService rankingQueryService) {
        this.rankingCommandService = rankingCommandService;
        this.rankingQueryService = rankingQueryService;
    }

    @PostMapping
    public ResponseEntity<RankingResource> createRanking(
            @RequestBody CreateRankingResource resource) {
        var createRankingCommand =
                CreateRankingCommandFromResourceAssembler.toCommandFromResource(resource);
        var rankingId = rankingCommandService.handle(createRankingCommand);
        var getRankingByIdQuery = new GetRankingByIdQuery(rankingId);
        var ranking = rankingQueryService.handle(getRankingByIdQuery);
        if (ranking.isPresent()) {
            var rankingResource =
                    RankingResourceFromEntityAssembler.toResourceFromEntity(ranking.get());
            return new ResponseEntity<>(rankingResource, HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<RankingResource>> getAllRankings() {
        var getAllRankingsQuery = new GetAllRankingsQuery();
        var rankings = rankingQueryService.handle(getAllRankingsQuery);
        var rankingResources = rankings.stream()
                .map(RankingResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(rankingResources);
    }

    @GetMapping("/{rankingId}")
    public ResponseEntity<RankingResource> getRankingById(
            @PathVariable Long rankingId) {
        var getRankingByIdQuery = new GetRankingByIdQuery(rankingId);
        var ranking = rankingQueryService.handle(getRankingByIdQuery);
        if (ranking.isPresent()) {
            var rankingResource =
                    RankingResourceFromEntityAssembler.toResourceFromEntity(ranking.get());
            return ResponseEntity.ok(rankingResource);
        }
        return ResponseEntity.notFound().build();
    }
}
