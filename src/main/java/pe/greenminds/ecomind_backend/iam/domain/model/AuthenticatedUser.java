package pe.greenminds.ecomind_backend.iam.domain.model;

import pe.greenminds.ecomind_backend.profile.domain.model.aggregates.User;

public record AuthenticatedUser(User user, String token) {
}
