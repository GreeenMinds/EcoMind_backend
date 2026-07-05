package pe.greenminds.ecomind_backend.learning.application.queryservices;

import pe.greenminds.ecomind_backend.learning.domain.model.aggregates.EducationalMaterial;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetAllEducationalMaterialsQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.GetEducationalMaterialByIdQuery;
import pe.greenminds.ecomind_backend.learning.domain.model.queries.SearchEducationalMaterialsQuery;

import java.util.List;
import java.util.Optional;

public interface EducationalMaterialQueryService {

    Optional<EducationalMaterial> handle(GetEducationalMaterialByIdQuery query);

    List<EducationalMaterial> handle(GetAllEducationalMaterialsQuery query);

    List<EducationalMaterial> handle(SearchEducationalMaterialsQuery query);
}
