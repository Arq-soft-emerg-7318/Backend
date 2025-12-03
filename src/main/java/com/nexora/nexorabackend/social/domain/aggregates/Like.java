package com.nexora.nexorabackend.social.domain.aggregates;

import com.nexora.nexorabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.nexora.nexorabackend.social.domain.commands.CreateLikeCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Like  extends AuditableAbstractAggregateRoot<Like> {

    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Long postId;

    
    public Like(CreateLikeCommand command) {
        this.userId = command.userId();
        this.postId = command.postId();
    }

    public Like() {
    }
}
