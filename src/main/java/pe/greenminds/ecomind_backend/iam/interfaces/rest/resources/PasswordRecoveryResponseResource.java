package pe.greenminds.ecomind_backend.iam.interfaces.rest.resources;

public record PasswordRecoveryResponseResource(String email, boolean sent) {
}
