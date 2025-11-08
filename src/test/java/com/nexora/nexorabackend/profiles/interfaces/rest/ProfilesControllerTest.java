package com.nexora.nexorabackend.profiles.interfaces.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.nexora.nexorabackend.profiles.domain.model.aggregates.Profile;
import com.nexora.nexorabackend.profiles.domain.model.valueobjects.PersonName;
import com.nexora.nexorabackend.profiles.domain.services.ProfileCommandService;
import com.nexora.nexorabackend.profiles.domain.services.ProfileQueryService;
import com.nexora.nexorabackend.profiles.domain.model.commands.CreateProfileCommand;
import com.nexora.nexorabackend.profiles.domain.model.queries.GetProfileByUserIdQuery;

@ExtendWith(MockitoExtension.class)
public class ProfilesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfileCommandService profileCommandService;

    @Mock
    private ProfileQueryService profileQueryService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProfilesController(profileCommandService, profileQueryService)).build();
        SecurityContextHolder.clearContext();
    }

    @Test
    void createProfile_withAuthenticatedUser_returnsCreated() throws Exception {
        UserDetailsImpl principal = new UserDetailsImpl(5L, "user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        var auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Profile saved = new Profile(new PersonName("John", "Doe"), "j@x.com", "dir", "123", "ID", 5L, "999");
        when(profileCommandService.handle(any(CreateProfileCommand.class))).thenReturn(Optional.of(saved));

        String payload = objectMapper.writeValueAsString(java.util.Map.of(
                "firstName", "John",
                "lastName", "Doe",
                "email", "j@x.com",
                "direction", "dir",
                "documentNumber", "123",
                "documentType", "ID",
                "phone", "999"
        ));

        mockMvc.perform(post("/api/v1/profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void getMyProfile_withAuthenticatedUser_returnsOk() throws Exception {
        UserDetailsImpl principal = new UserDetailsImpl(5L, "user", "pass",
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        var auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        Profile found = new Profile(new PersonName("Jane", "Smith"), "j@x.com", "dir", "456", "ID", 5L, "555");
        when(profileQueryService.handle(any(GetProfileByUserIdQuery.class))).thenReturn(Optional.of(found));

        mockMvc.perform(get("/api/v1/profiles/" + 5)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.userId").value(5));
    }

}
