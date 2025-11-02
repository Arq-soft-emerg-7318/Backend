package com.nexora.nexorabackend.social.domain.commands;

import java.util.Objects;


public record CreatePostCommand(String title, Integer authorId, String body, Integer reactions, Integer categoryId,
        Integer fileId, Integer communityId) {

            public CreatePostCommand {

                Objects.requireNonNull(title, "Title cannot be null");
                Objects.requireNonNull(authorId, "Author ID cannot be null");
                Objects.requireNonNull(body, "Body cannot be null");
                // Objects.requireNonNull(reactions, "Reactions cannot be null");
                Objects.requireNonNull(categoryId, "Category ID cannot be null");
                Objects.requireNonNull(fileId, "File ID cannot be null");
                Objects.requireNonNull(communityId, "Community ID cannot be null");
            }
}