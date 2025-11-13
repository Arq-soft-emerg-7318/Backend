package com.nexora.nexorabackend.community.infrastructure.repositories;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    Optional<Community> findCommunityById(Long id);
}
