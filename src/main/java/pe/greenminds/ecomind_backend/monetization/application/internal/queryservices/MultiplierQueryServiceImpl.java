package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.MultiplierQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Multiplier;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllMultipliersQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetMultiplierByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.MultiplierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MultiplierQueryServiceImpl implements MultiplierQueryService {

    private final MultiplierRepository multiplierRepository;

    public MultiplierQueryServiceImpl(MultiplierRepository multiplierRepository) {
        this.multiplierRepository = multiplierRepository;
    }

    @Override
    public Optional<Multiplier> handle(GetMultiplierByIdQuery query) {
        return multiplierRepository.findById(query.id());
    }

    @Override
    public List<Multiplier> handle(GetAllMultipliersQuery query) {
        return multiplierRepository.findAll();
    }
}
