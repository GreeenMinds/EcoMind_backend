package pe.greenminds.ecomind_backend.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(
        Long id,
        String name,
        String email,
        String token
) {
}
