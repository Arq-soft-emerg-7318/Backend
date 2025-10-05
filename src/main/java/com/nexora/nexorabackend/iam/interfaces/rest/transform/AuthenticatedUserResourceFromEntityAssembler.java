package com.nexora.nexorabackend.iam.interfaces.rest.transform;


import com.nexora.nexorabackend.iam.domain.model.aggregates.User;
import com.nexora.nexorabackend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}
