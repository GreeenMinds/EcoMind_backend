package pe.greenminds.ecomind_backend.iam.application.outboundservices;

import jakarta.servlet.http.HttpServletRequest;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;

public interface TokenService {
    String generateToken(User user);
    boolean validateToken(String token);
    Long getUserIdFromToken(String token);
    String getBearerTokenFrom(HttpServletRequest request);
}
