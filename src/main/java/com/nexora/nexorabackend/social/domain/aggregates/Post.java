package com.nexora.nexorabackend.social.domain.aggregates;

import com.nexora.nexorabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.nexora.nexorabackend.social.domain.commands.CreatePostCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Post extends AuditableAbstractAggregateRoot<Post> {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String body;

    @Column(nullable = true)
    private Integer reactions;

    @Column(nullable = false)
    private Integer categoryId;

    @Column(nullable = true)
    private Integer fileId;
    
    @Column(nullable = true)
    private Integer communityId;
    
    public Post() {}

    public Post(String title, Long authorId, String body, Integer reactions, Integer categoryId, Integer fileId,
            Integer communityId) {
        this.title = title;
        this.authorId = authorId;
        this.body = body;
        this.reactions = reactions;
        this.categoryId = categoryId;
        this.fileId = fileId;
        this.communityId = communityId;
    }

    public Post(CreatePostCommand command) {
        this.title = command.title();
        this.authorId = command.authorId();
        this.body = command.body();
        this.reactions = command.reactions();
        this.categoryId = command.categoryId();
        this.fileId = command.fileId();
        this.communityId = command.communityId();
    }
    
}

