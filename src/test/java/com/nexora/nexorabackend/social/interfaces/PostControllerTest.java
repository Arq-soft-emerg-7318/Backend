// package com.nexora.nexorabackend.social.interfaces;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.util.List;
// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
// import com.nexora.nexorabackend.social.domain.aggregates.Post;
// import com.nexora.nexorabackend.social.domain.services.PostCommandService;

// @ExtendWith(MockitoExtension.class)
// public class PostControllerTest {

//     private MockMvc mockMvc;

//     @Mock
//     private PostCommandService postCommandService;

//     @Mock
//     private com.nexora.nexorabackend.shared.Infrastructure.services.StorageService storageService;

//     @Mock
//     private com.nexora.nexorabackend.social.domain.services.PostQueryService postQueryService;

//     private final ObjectMapper objectMapper = new ObjectMapper();

//     @BeforeEach
//     void setup() {
//     this.mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postCommandService, postQueryService, storageService)).build();
//         SecurityContextHolder.clearContext();
//     }

//     @Test
//     void createPost_withAuthenticatedUser_returnsCreated() throws Exception {
//         UserDetailsImpl principal = new UserDetailsImpl(10L, "user", "pass",
//                 List.of(new SimpleGrantedAuthority("ROLE_USER")));
//         var auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
//         SecurityContextHolder.getContext().setAuthentication(auth);

//         Post saved = new Post();
//         when(postCommandService.handle(any())).thenReturn(Optional.of(saved));
//         when(storageService.store(any())).thenReturn(1);

//     String payload = objectMapper.writeValueAsString(java.util.Map.of(
//         "title", "Hello",
//         "authorId", 0,
//         "body", "Body",
//         "reactions", 0,
//         "categoryId", 1,
//         "fileId", 1,
//         "communityId", 2
//     ));

//     org.springframework.mock.web.MockMultipartFile postPart = new org.springframework.mock.web.MockMultipartFile("post", "post", "application/json", payload.getBytes());
//     org.springframework.mock.web.MockMultipartFile filePart = new org.springframework.mock.web.MockMultipartFile("file", "test.txt", "text/plain", "hello".getBytes());

//     mockMvc.perform(multipart("/api/v1/posts").file(postPart).file(filePart).accept(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.title").value("Hello"))
//                 .andExpect(jsonPath("$.authorId").value(10));
//     }

// }
