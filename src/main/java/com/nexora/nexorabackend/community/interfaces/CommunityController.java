package com.nexora.nexorabackend.community.interfaces;

import com.nexora.nexorabackend.community.domain.model.aggregates.Community;
import com.nexora.nexorabackend.community.domain.model.queries.GetAllCommunitiesQuery;
import com.nexora.nexorabackend.community.domain.model.queries.GetCommunityByIdQuery;
import com.nexora.nexorabackend.community.domain.services.CommunityCommandService;
import com.nexora.nexorabackend.community.domain.services.CommunityQueryService;
import com.nexora.nexorabackend.community.interfaces.rest.resources.CommunityResource;
import com.nexora.nexorabackend.community.interfaces.rest.resources.CreateCommunityResource;
import com.nexora.nexorabackend.community.interfaces.rest.transform.CommunityResourceFromEntityAssembler;
import com.nexora.nexorabackend.community.interfaces.rest.transform.CreateCommunityCommandFromResourceAssembler;
import com.nexora.nexorabackend.iam.domain.model.entities.Role;
import com.nexora.nexorabackend.iam.domain.model.valueobjects.Roles;
import com.nexora.nexorabackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.nexora.nexorabackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/communities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunityController {
    private final CommunityCommandService communityCommandService;
    private final CommunityQueryService communityQueryService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CommunityController(CommunityCommandService communityCommandService,
                               CommunityQueryService communityQueryService,
                               UserRepository userRepository,
                               RoleRepository roleRepository) {
        this.communityCommandService = communityCommandService;
        this.communityQueryService = communityQueryService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CommunityResource> createCommunity(@RequestBody CreateCommunityResource resource) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));

        boolean hasUserRole = user.getRoles().stream()
                .anyMatch(role -> Roles.USER.equals(role.getName()));

        if (!hasUserRole) {
            throw new AccessDeniedException("User is not permitted to create a community");
        }

        var command = CreateCommunityCommandFromResourceAssembler.toCommandFromResource(resource, userId);

        var community = communityCommandService.handle(command)
                .orElseThrow(() -> new IllegalStateException("The community could not be created"));

        Role communityAdminRole = roleRepository.findByName(Roles.COMMUNITY_ADMIN)
                .orElseGet(() -> roleRepository.save(new Role(Roles.COMMUNITY_ADMIN)));

        user.getRoles().removeIf(role -> Roles.USER.equals(role.getName()));
        user.addRole(communityAdminRole);
        userRepository.save(user);

        var communityResource = CommunityResourceFromEntityAssembler.toResourceFromEntity(community);
        return ResponseEntity.status(HttpStatus.CREATED).body(communityResource);
    }

    @GetMapping
    public ResponseEntity<List<Community>> listAllCommunities() {
        var communities = communityQueryService.handle(new GetAllCommunitiesQuery());
        return ResponseEntity.ok(communities);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityResource> getCommunityById(@PathVariable Long communityId) {
        var community = communityQueryService.handle(new GetCommunityByIdQuery(communityId));
        if (community.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var communityResource = CommunityResourceFromEntityAssembler.toResourceFromEntity(community.get());
        return ResponseEntity.ok(communityResource);
    }



    @PostMapping("/{communityId}/join")
    @Transactional
    public ResponseEntity<Void> joinCommunity(@PathVariable Long communityId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        var user = userRepository.findById(userId)
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));

        var community = communityQueryService.handle(new GetCommunityByIdQuery(communityId));
        if (community.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        boolean hasUserRole = user.getRoles().stream()
                .anyMatch(role -> Roles.USER.equals(role.getName()));

        if (!hasUserRole) {
            throw new AccessDeniedException("User is not permitted to join a community");
        }

        boolean alreadyMember = user.getRoles().stream()
                .anyMatch(role -> Roles.COMMUNITY_MEMBER.equals(role.getName()));

        if (!alreadyMember) {
            Role communityMemberRole = roleRepository.findByName(Roles.COMMUNITY_MEMBER)
                    .orElseGet(() -> roleRepository.save(new Role(Roles.COMMUNITY_MEMBER)));

            user.getRoles().removeIf(role -> Roles.USER.equals(role.getName()));
            user.addRole(communityMemberRole);
            userRepository.save(user);
        }

        return ResponseEntity.noContent().build();
    }


}
