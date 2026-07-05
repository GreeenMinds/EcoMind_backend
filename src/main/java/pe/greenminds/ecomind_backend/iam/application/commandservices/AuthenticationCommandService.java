package pe.greenminds.ecomind_backend.iam.application.commandservices;

import pe.greenminds.ecomind_backend.iam.domain.model.AuthenticatedUser;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.SignInResource;
import pe.greenminds.ecomind_backend.iam.interfaces.rest.resources.SignUpResource;
import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;
import pe.greenminds.ecomind_backend.shared.application.result.ApplicationError;
import pe.greenminds.ecomind_backend.shared.application.result.Result;

public interface AuthenticationCommandService {
    Result<AuthenticatedUser, ApplicationError> signUp(SignUpResource resource);
    Result<AuthenticatedUser, ApplicationError> signIn(SignInResource resource);
    Result<User, ApplicationError> me(Long userId);
    Result<User, ApplicationError> deleteAccount(Long userId);
    Result<String, ApplicationError> requestPasswordRecovery(String email);
    Result<Void, ApplicationError> confirmPasswordRecovery(String token, String newPassword);
}
