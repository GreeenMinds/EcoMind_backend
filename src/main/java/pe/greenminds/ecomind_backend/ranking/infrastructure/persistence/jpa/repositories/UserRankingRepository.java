package pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.UserRanking;

import java.util.List;

@Repository
public interface UserRankingRepository extends JpaRepository<UserRanking, Long> {
    List<UserRanking> findByRankingId(Long rankingId);
    List<UserRanking> findByUserId(Long userId);
}
