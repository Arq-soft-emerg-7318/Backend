package com.nexora.nexorabackend.iam.interfaces.rest.transform;


import com.nexora.nexorabackend.iam.domain.model.entities.Role;
import com.nexora.nexorabackend.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
