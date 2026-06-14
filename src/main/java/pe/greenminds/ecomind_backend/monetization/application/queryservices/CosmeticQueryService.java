package pe.greenminds.ecomind_backend.monetization.application.queryservices;

import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllCosmeticsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetCosmeticByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CosmeticQueryService {

    Optional<Cosmetic> handle(GetCosmeticByIdQuery query);

    List<Cosmetic> handle(GetAllCosmeticsQuery query);
}
