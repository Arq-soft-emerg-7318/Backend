package com.nexora.nexorabackend.social.interfaces;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.nexora.nexorabackend.social.domain.aggregates.Like;
import com.nexora.nexorabackend.social.domain.commands.CreateLikeCommand;
import com.nexora.nexorabackend.social.domain.services.LikeCommandService;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LikeCommandService likeCommand;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        // standalone setup avoids loading Spring context (no JPA/auditing auto-config)
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LikeController(likeCommand, null)).build();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void createLike_withAuthenticatedUser_returnsCreated() throws Exception {
        // prepare authentication with UserDetailsImpl
        UserDetailsImpl principal = new UserDetailsImpl(1L, "user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        var auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        // mock service
        Like saved = new Like(new CreateLikeCommand(1L, 2L));
        when(likeCommand.handle(any(CreateLikeCommand.class))).thenReturn(Optional.of(saved));

        String payload = objectMapper.writeValueAsString(java.util.Map.of("postId", 2));

        mockMvc.perform(post("/api/v1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.postId").value(2));
    }

    @Test
    void createLike_withoutUserId_returnsUnauthorized() throws Exception {
        // no authentication and no userId in body
        String payload = objectMapper.writeValueAsString(java.util.Map.of("postId", 2));

        mockMvc.perform(post("/api/v1/likes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
