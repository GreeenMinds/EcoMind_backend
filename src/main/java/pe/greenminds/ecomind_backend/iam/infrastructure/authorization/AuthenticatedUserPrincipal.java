package pe.greenminds.ecomind_backend.iam.infrastructure.authorization;

public record AuthenticatedUserPrincipal(Long userId, String email, String name) {
}
