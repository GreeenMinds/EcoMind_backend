package pe.greenminds.ecomind_backend.ranking.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.greenminds.ecomind_backend.ranking.domain.model.valueobjects.RankingType;
import pe.greenminds.ecomind_backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Entity
@Table(name = "rankings")
public class Ranking extends AuditableAbstractAggregateRoot<Ranking> {

    @Getter
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private RankingType type;

    @Getter
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Getter
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Getter
    @Column(name = "status", nullable = false)
    private boolean status;

    protected Ranking() {}

    public Ranking(String name, RankingType type, Date startDate, Date endDate, boolean status) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Ranking(String name, RankingType type) {
        this(name, type, new Date(), null, true);
    }

    public void updateInformation(String name, RankingType type, Date startDate, Date endDate, boolean status) {
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
}
