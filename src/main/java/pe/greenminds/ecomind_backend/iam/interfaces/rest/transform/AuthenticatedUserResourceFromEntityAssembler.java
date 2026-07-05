package pe.greenminds.ecomind_backend.iam.interfaces.rest.transform;

import pe.greenminds.ecomind_backend.iam.domain.model.AuthenticatedUser;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public final class AuthenticatedUserResourceFromEntityAssembler {
    private AuthenticatedUserResourceFromEntityAssembler() {
    }

    public static AuthenticatedUserResource toResourceFromEntity(AuthenticatedUser authenticatedUser) {
        var user = authenticatedUser.user();
        return new AuthenticatedUserResource(user.getId(), user.getName(), user.getEmail(), authenticatedUser.token());
    }
}
