package com.nexora.nexorabackend.social.interfaces;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

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
import com.nexora.nexorabackend.social.domain.aggregates.Post;
import com.nexora.nexorabackend.social.domain.services.PostCommandService;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostCommandService postCommandService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postCommandService, null)).build();
        SecurityContextHolder.clearContext();
    }

    @Test
    void createPost_withAuthenticatedUser_returnsCreated() throws Exception {
        UserDetailsImpl principal = new UserDetailsImpl(10L, "user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        var auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Post saved = new Post();
        when(postCommandService.handle(any())).thenReturn(Optional.of(saved));

    String payload = objectMapper.writeValueAsString(java.util.Map.of(
        "title", "Hello",
        "authorId", 0,
        "body", "Body",
        "reactions", 0,
        "categoryId", 1,
        "fileId", 1,
        "communityId", 2
    ));

        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Hello"))
                .andExpect(jsonPath("$.authorId").value(10));
    }

}
