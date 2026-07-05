package pe.greenminds.ecomind_backend.iam.application;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.greenminds.ecomind_backend.iam.infrastructure.authorization.AuthenticatedUserPrincipal;

import java.util.Optional;

@Service
public class CurrentAuthenticatedUserService {
    public Optional<Long> currentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return Optional.empty();
        }
        if (authentication.getPrincipal() instanceof AuthenticatedUserPrincipal principal) {
            return Optional.of(principal.userId());
        }
        return Optional.empty();
    }
}
