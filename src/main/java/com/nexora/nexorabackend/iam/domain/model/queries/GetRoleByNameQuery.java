package com.nexora.nexorabackend.iam.domain.model.queries;


import com.nexora.nexorabackend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles roleName) {
}
