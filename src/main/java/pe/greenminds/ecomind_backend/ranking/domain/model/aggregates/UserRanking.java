package pe.greenminds.ecomind_backend.ranking.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "user_rankings")
public class UserRanking extends AuditableAbstractAggregateRoot<UserRanking> {

    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Getter
    @Column(name = "ranking_id", nullable = false)
    private Long rankingId;

    @Getter
    @Column(name = "rank_position", nullable = false)
    private int rankPosition;

    @Getter
    @Column(name = "score", nullable = false)
    private int score;

    protected UserRanking() {}

    public UserRanking(Long userId, Long rankingId, int rankPosition, int score) {
        this.userId = userId;
        this.rankingId = rankingId;
        this.rankPosition = rankPosition;
        this.score = score;
    }

    public void updateInformation(Long userId, Long rankingId, int rankPosition, int score) {
        this.userId = userId;
        this.rankingId = rankingId;
        this.rankPosition = rankPosition;
        this.score = score;
    }
}
