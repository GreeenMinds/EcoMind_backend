package pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources;

public record CreateUserRankingResource(Long userId, Long rankingId, int rankPosition, int score) {}
