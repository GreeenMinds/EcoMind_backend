package pe.greenminds.ecomind_backend.iam.infrastructure.tokens.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pe.greenminds.ecomind_backend.iam.application.outboundservices.TokenService;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

@Service
public class TokenServiceImpl implements TokenService {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    @Override
    public String generateToken(User user) {
        var issuedAt = Instant.now();
        var expiration = issuedAt.plus(expirationDays, ChronoUnit.DAYS);
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(getSigningKey())
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                 IllegalArgumentException ignored) {
            return false;
        }
    }

    @Override
    public Long getUserIdFromToken(String token) {
        return Long.valueOf(extractClaim(token, Claims::getSubject));
    }

    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        var authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (!StringUtils.hasText(authorization) || !authorization.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return authorization.substring(BEARER_PREFIX.length());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        return claimsResolver.apply(claims);
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
