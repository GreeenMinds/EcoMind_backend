package pe.greenminds.ecomind_backend.achievements.application.outboundservices.external;

import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.profile.domain.repositories.FamilyUserRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.util.List;

@Service
public class ProfileAchievementExternalService {
    private final FamilyUserRepository familyUserRepository;
    private final UserRepository userRepository;

    public ProfileAchievementExternalService(
            FamilyUserRepository familyUserRepository,
            UserRepository userRepository
    ) {
        this.familyUserRepository = familyUserRepository;
        this.userRepository = userRepository;
    }

    public List<Long> fetchFamilyMemberUserIds(Long familyId) {
        return familyUserRepository.findByFamilyId(familyId).stream()
                .map(familyUser -> familyUser.getUserId())
                .toList();
    }

    public List<Long> fetchCommunityMemberUserIds(Long communityId) {
        return userRepository.findAll().stream()
                .filter(user -> communityId.equals(user.getCommunityId()))
                .map(user -> user.getId())
                .toList();
    }

    public Integer fetchUserEcopointsTotal(Long userId) {
        return userRepository.findById(userId)
                .map(user -> user.getEcopoints() == null ? 0 : user.getEcopoints())
                .orElse(0);
    }

    public Integer fetchFamilyEcopointsTotal(Long familyId) {
        return fetchFamilyMemberUserIds(familyId).stream()
                .map(userRepository::findById)
                .flatMap(java.util.Optional::stream)
                .mapToInt(user -> user.getEcopoints() == null ? 0 : user.getEcopoints())
                .sum();
    }

    public Integer fetchCommunityEcopointsTotal(Long communityId) {
        return fetchCommunityMemberUserIds(communityId).stream()
                .map(userRepository::findById)
                .flatMap(java.util.Optional::stream)
                .mapToInt(user -> user.getEcopoints() == null ? 0 : user.getEcopoints())
                .sum();
    }
}
