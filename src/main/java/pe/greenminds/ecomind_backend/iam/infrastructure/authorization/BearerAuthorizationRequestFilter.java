package pe.greenminds.ecomind_backend.iam.infrastructure.authorization;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import pe.greenminds.ecomind_backend.iam.application.outboundservices.TokenService;
import pe.greenminds.ecomind_backend.iam.infrastructure.persistence.jpa.repositories.AccountCredentialPersistenceRepository;
import pe.greenminds.ecomind_backend.profile.domain.repositories.UserRepository;

import java.io.IOException;
import java.util.List;

public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final AccountCredentialPersistenceRepository credentialRepository;
    private final UserRepository userRepository;

    public BearerAuthorizationRequestFilter(TokenService tokenService,
                                            AccountCredentialPersistenceRepository credentialRepository,
                                            UserRepository userRepository) {
        this.tokenService = tokenService;
        this.credentialRepository = credentialRepository;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = tokenService.getBearerTokenFrom(request);
        if (token != null && tokenService.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userId = tokenService.getUserIdFromToken(token);
            var user = userRepository.findById(userId);
            var credential = credentialRepository.findByUserId(userId);
            if (user.isPresent() && credential.isPresent()) {
                var principal = new AuthenticatedUserPrincipal(userId, user.get().getEmail(), user.get().getName());
                var authentication = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
