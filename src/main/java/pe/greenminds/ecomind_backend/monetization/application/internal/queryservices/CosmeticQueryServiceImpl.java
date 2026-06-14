package pe.greenminds.ecomind_backend.monetization.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.monetization.application.queryservices.CosmeticQueryService;
import pe.greenminds.ecomind_backend.monetization.domain.model.aggregates.Cosmetic;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetAllCosmeticsQuery;
import pe.greenminds.ecomind_backend.monetization.domain.model.queries.GetCosmeticByIdQuery;
import pe.greenminds.ecomind_backend.monetization.domain.repositories.CosmeticRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CosmeticQueryServiceImpl implements CosmeticQueryService {

    private final CosmeticRepository cosmeticRepository;

    public CosmeticQueryServiceImpl(CosmeticRepository cosmeticRepository) {
        this.cosmeticRepository = cosmeticRepository;
    }

    @Override
    public Optional<Cosmetic> handle(GetCosmeticByIdQuery query) {
        return cosmeticRepository.findById(query.id());
    }

    @Override
    public List<Cosmetic> handle(GetAllCosmeticsQuery query) {
        return cosmeticRepository.findAll();
    }
}
