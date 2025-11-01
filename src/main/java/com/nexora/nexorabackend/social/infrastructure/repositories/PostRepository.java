package com.nexora.nexorabackend.social.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexora.nexorabackend.social.domain.aggregates.Post;

@Repository
public interface PostRepository  extends JpaRepository<Post, Long> {
    Optional<Post> findById(Integer id);
    
}
