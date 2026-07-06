package pe.greenminds.ecomind_backend.learning.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.learning.application.queryservices.EducationalMaterialQueryService;
import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetAllEducationalMaterialsQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetEducationalMaterialByIdQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.SearchEducationalMaterialsQuery;
import pe.greenminds.ecomind_backend.learning.domain.repositories.EducationalMaterialRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EducationalMaterialQueryServiceImpl implements EducationalMaterialQueryService {

    private final EducationalMaterialRepository educationalMaterialRepository;

    public EducationalMaterialQueryServiceImpl(EducationalMaterialRepository educationalMaterialRepository) {
        this.educationalMaterialRepository = educationalMaterialRepository;
    }

    @Override
    public Optional<EducationalMaterial> handle(GetEducationalMaterialByIdQuery query) {
        return educationalMaterialRepository.findById(query.id());
    }

    @Override
    public List<EducationalMaterial> handle(GetAllEducationalMaterialsQuery query) {
        return educationalMaterialRepository.findAll();
    }

    @Override
    public List<EducationalMaterial> handle(SearchEducationalMaterialsQuery query) {
        return educationalMaterialRepository.search(
                query.title(),
                query.category(),
                query.materialType()
        );
    }
}
