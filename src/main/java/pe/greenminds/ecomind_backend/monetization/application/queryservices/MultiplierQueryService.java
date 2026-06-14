package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllMultipliersQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetMultiplierByIdQuery;

import java.util.List;
import java.util.Optional;

public interface MultiplierQueryService {

    Optional<Multiplier> handle(GetMultiplierByIdQuery query);

    List<Multiplier> handle(GetAllMultipliersQuery query);
}
