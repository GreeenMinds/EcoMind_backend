package pe.greenminds.ecomind_backend.quests.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.quests.domain.model.aggregates.ActivityUser;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByActivityIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetActivityUserByUserIdQuery;
import pe.greenminds.ecomind_backend.quests.domain.model.queries.GetAllActivityUsersQuery;
import pe.greenminds.ecomind_backend.quests.domain.services.ActivityUserQueryService;
import pe.greenminds.ecomind_backend.quests.domain.repositories.ActivityUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityUserQueryServiceImpl implements ActivityUserQueryService {

    private final ActivityUserRepository activityUserRepository;

    public ActivityUserQueryServiceImpl(ActivityUserRepository activityUserRepository) {
        this.activityUserRepository = activityUserRepository;
    }

    @Override
    public Optional<ActivityUser> handle(GetActivityUserByIdQuery query) {
        return activityUserRepository.findById(query.id());
    }

    @Override
    public List<ActivityUser> handle(GetAllActivityUsersQuery query) {
        return activityUserRepository.findAll();
    }

    @Override
    public List<ActivityUser> handle(GetActivityUserByUserIdQuery query) {
        return activityUserRepository.findByUserId(query.userId());
    }

    @Override
    public List<ActivityUser> handle(GetActivityUserByActivityIdQuery query) {
        return activityUserRepository.findByActivityId(query.activityId());
    }
}
