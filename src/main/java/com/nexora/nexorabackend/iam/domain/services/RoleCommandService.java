package com.nexora.nexorabackend.iam.domain.services;


import com.nexora.nexorabackend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
