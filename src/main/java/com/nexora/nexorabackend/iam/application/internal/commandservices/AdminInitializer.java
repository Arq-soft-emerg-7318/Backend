package com.nexora.nexorabackend.iam.application.internal.commandservices;

import com.nexora.nexorabackend.iam.domain.model.commands.SignUpCommand;
import com.nexora.nexorabackend.iam.domain.model.entities.Role;
import com.nexora.nexorabackend.iam.domain.model.valueobjects.Roles;
import com.nexora.nexorabackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.nexora.nexorabackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdminInitializer {

    private final SignUpHandler signUpHandler;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AdminInitializer(SignUpHandler signUpHandler, UserRepository userRepository, RoleRepository roleRepository) {
        this.signUpHandler = signUpHandler;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    @Transactional
    public void initAdminUser() {
        if (userRepository.existsByUsername("admin")) {
            System.out.println("ℹ️ Usuario admin ya existe.");
            return;
        }
        List<Role> roles = getRolesFromEnumNames(List.of(Roles.USER));

        // ✅ Asignamos el rol admin explícitamente
        signUpHandler.handle(new SignUpCommand("admin", "admin123", roles));
        System.out.println("✅ Usuario admin creado con éxito.");
    }

    private List<Role> getRolesFromEnumNames(List<Roles> roleEnums) {
        return roleEnums.stream()
                .map(roleEnum -> roleRepository.findByName(roleEnum)
                        .orElseGet(() -> roleRepository.save(new Role(roleEnum))))
                .collect(Collectors.toList());
    }

}
