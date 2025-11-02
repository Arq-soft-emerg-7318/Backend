package com.nexora.nexorabackend.social.domain.commands;

import java.util.Objects;

public record CreateLikeCommand (Integer userId, Integer postId) {

    public CreateLikeCommand {
        Objects.requireNonNull(userId, "User ID cannot be null");
        Objects.requireNonNull(postId, "Post ID cannot be null");
    }

}