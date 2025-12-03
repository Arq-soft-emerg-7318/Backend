// package com.nexora.nexorabackend.subscription.interfaces.rest;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.sql.Date;
// import java.time.LocalDate;
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
// import com.nexora.nexorabackend.subscription.domain.aggregates.Subscription;
// import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;
// import com.nexora.nexorabackend.subscription.domain.services.SubscriptionCommandService;

// @ExtendWith(MockitoExtension.class)
// public class SubscriptionControllerTest {

//     private MockMvc mockMvc;

//     @Mock
//     private SubscriptionCommandService subscriptionCommandService;

//     private final ObjectMapper objectMapper = new ObjectMapper();

//     @BeforeEach
//     void setup() {
//         this.mockMvc = MockMvcBuilders.standaloneSetup(new SubscriptionController(subscriptionCommandService, null)).build();
//         SecurityContextHolder.clearContext();
//     }

//     @Test
//     void createSubscription_withAuthenticatedUser_returnsOk() throws Exception {
//         UserDetailsImpl principal = new UserDetailsImpl(7L, "user", "pass",
//                 List.of(new SimpleGrantedAuthority("ROLE_USER")));
//         var auth = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
//         SecurityContextHolder.getContext().setAuthentication(auth);

//         CreateSubscriptionCommand cmd = new CreateSubscriptionCommand("Pro", 9.99f, true, 7L, Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusMonths(1)));
//         Subscription sub = new Subscription(cmd);
//         when(subscriptionCommandService.handle(any(CreateSubscriptionCommand.class))).thenReturn(Optional.of(sub));

//         String payload = objectMapper.writeValueAsString(java.util.Map.of(
//                 "planName", "Pro",
//                 "price", 9.99,
//                 "enable", true,
//                 "starDate", Date.valueOf(LocalDate.now()),
//                 "endDate", Date.valueOf(LocalDate.now().plusMonths(1))
//         ));

//         mockMvc.perform(post("/api/v1/subscriptions")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(payload)
//                 .accept(MediaType.APPLICATION_JSON))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.planName").value("Pro"))
//                 .andExpect(jsonPath("$.userId").value(7));
//     }

// }
