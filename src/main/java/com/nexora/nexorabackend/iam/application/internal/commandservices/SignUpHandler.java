package com.nexora.nexorabackend.iam.application.internal.commandservices;

import com.nexora.nexorabackend.iam.application.internal.outboundservices.hashing.HashingService;
import com.nexora.nexorabackend.iam.domain.model.aggregates.User;
import com.nexora.nexorabackend.iam.domain.model.commands.SignUpCommand;
import com.nexora.nexorabackend.iam.domain.model.entities.Role;
import com.nexora.nexorabackend.iam.domain.model.valueobjects.Roles;
import com.nexora.nexorabackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.nexora.nexorabackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component

public class SignUpHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;

    public SignUpHandler(UserRepository userRepository, RoleRepository roleRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
    }


    @Transactional
    public Optional<User> handle(SignUpCommand command) {
        if (userRepository.existsByUsername(command.username()))
            return Optional.empty();

        List<Role> roles;

        if (command.roles() != null && !command.roles().isEmpty()) {
            // ✅ Asegurar que todos los roles existan (o crearlos si no)
            roles = command.roles().stream()
                    .map(role -> roleRepository.findByName(role.getName())
                            .orElseGet(() -> roleRepository.save(new Role(role.getName()))))
                    .toList();
        } else {
            // ⚠️ Si no vienen roles, usar USER por defecto
            Role providerRole = roleRepository.findByName(Roles.USER)
                    .orElseGet(() -> roleRepository.save(new Role(Roles.USER)));
            roles = List.of(providerRole);
        }

        var user = new User(command.username(), hashingService.encode(command.password()), roles);
        userRepository.save(user);
        return userRepository.findByUsername(command.username());
    }

}