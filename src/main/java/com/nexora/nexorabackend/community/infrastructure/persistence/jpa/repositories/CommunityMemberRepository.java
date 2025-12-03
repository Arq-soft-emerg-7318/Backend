package com.nexora.nexorabackend.community.infrastructure.persistence.jpa.repositories;

import com.nexora.nexorabackend.community.infrastructure.persistence.jpa.entities.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    Optional<CommunityMember> findByUserIdAndCommunityId(Long userId, Long communityId);
    List<CommunityMember> findByUserId(Long userId);
}
