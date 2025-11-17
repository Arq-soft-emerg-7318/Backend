package com.nexora.nexorabackend.community.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "community_members", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "community_id"}))
public class CommunityMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "community_id", nullable = false)
    private Long communityId;

    public CommunityMember() {}

    public CommunityMember(Long userId, Long communityId) {
        this.userId = userId;
        this.communityId = communityId;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCommunityId() {
        return communityId;
    }
}
