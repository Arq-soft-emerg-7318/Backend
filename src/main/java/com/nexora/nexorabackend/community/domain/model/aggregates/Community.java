package com.nexora.nexorabackend.community.domain.model.aggregates;

import com.nexora.nexorabackend.community.domain.model.commands.CreateCommunityCommand;
import com.nexora.nexorabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Community extends AuditableAbstractAggregateRoot<Community> {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Long userId;

    public Community() {}

    public Community(String name, String description, Long userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public Community(CreateCommunityCommand command) {
        this.name = command.name();
        this.description = command.description();
        this.userId = command.userId();
    }
}
