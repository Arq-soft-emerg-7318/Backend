package com.nexora.nexorabackend.social.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nexora.nexorabackend.social.domain.aggregates.Like;

@Repository
public interface LikeRepository  extends JpaRepository<Like, Long> {
    Optional<Like> findById(Integer id);

    long countLikesByPostId(Long postId);
}
