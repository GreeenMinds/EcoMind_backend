package pe.greenminds.ecomind_backend.achievements.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.community.domain.repositories.EventRepository;
import pe.greenminds.ecomind_backend.community.domain.repositories.PostRepository;

@Service
public class CommunityAchievementExternalService {
    private final PostRepository postRepository;
    private final EventRepository eventRepository;

    public CommunityAchievementExternalService(
            PostRepository postRepository,
            EventRepository eventRepository
    ) {
        this.postRepository = postRepository;
        this.eventRepository = eventRepository;
    }

    public int countPostsByCommunityId(Long communityId) {
        return postRepository.countByCommunityId(communityId);
    }

    public int countEventsByCommunityId(Long communityId) {
        return eventRepository.countByCommunityId(communityId);
    }
}
