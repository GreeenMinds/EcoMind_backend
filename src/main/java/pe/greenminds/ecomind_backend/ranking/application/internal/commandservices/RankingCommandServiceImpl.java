package pe.greenminds.ecomind_backend.ranking.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.ranking.domain.model.aggregates.Ranking;
import pe.greenminds.ecomind_backend.ranking.domain.model.commands.CreateRankingCommand;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.ranking.domain.services.RankingCommandService;
import pe.greenminds.ecomind_backend.ranking.infrastructure.persistence.jpa.repositories.RankingRepository;

import java.util.Date;

@Service
public class RankingCommandServiceImpl implements RankingCommandService {

    private final RankingRepository rankingRepository;

    public RankingCommandServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }

    @Override
    public Long handle(CreateRankingCommand command) {
        if (rankingRepository.existsByName(command.name())) {
            throw new IllegalArgumentException("Ranking with name " + command.name()
                    + " already exists");
        }
        var rankingType = RankingType.valueOf(command.type().toUpperCase());
        var ranking = new Ranking(command.name(), rankingType, new Date(), null, true);
        return rankingRepository.save(ranking).getId();
    }
}
