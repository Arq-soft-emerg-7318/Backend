package com.nexora.nexorabackend.subscription.interfaces.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexora.nexorabackend.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;

import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;
import com.nexora.nexorabackend.subscription.domain.services.SubscriptionCommandService;
import com.nexora.nexorabackend.subscription.domain.services.SubscriptionQueryService;
import com.nexora.nexorabackend.subscription.interfaces.rest.resources.CreateSubscriptionResource;
import com.nexora.nexorabackend.subscription.interfaces.rest.resources.SubscriptionResource;
import com.nexora.nexorabackend.subscription.interfaces.rest.transform.CreateSubscriptionCommandFromResourceAssembler;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;

@RestController
@RequestMapping(value= "/api/v1/subscriptions", produces =  MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscription Management Endpoints")
@PreAuthorize("isAuthenticated()")
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionController(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
        this.subscriptionQueryService = subscriptionQueryService;
    }
    @PostMapping
    public ResponseEntity<SubscriptionResource> createSubscription(@RequestBody CreateSubscriptionResource resource) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }


            if (!(authentication.getPrincipal() instanceof UserDetailsImpl)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Long userId = userDetails.getId();

            CreateSubscriptionCommand createSubscriptionCommand =
                    CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource, userId);


            var subscription = subscriptionCommandService.handle(createSubscriptionCommand);

            if (subscription.isPresent()) {
                var sub = subscription.get();

                var subscriptionResource = new SubscriptionResource(
                        sub.getId(),
                        sub.getPlanName(),
                        sub.getPrice(),
                        sub.isEnable(),
                        sub.getUserId()
                );

                return ResponseEntity.ok(subscriptionResource);
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/admin")
    public ResponseEntity<List<SubscriptionResource>> getAllSubscriptionsForAdmin() {
        List <SubscriptionResource> subscriptions = subscriptionQueryService.handle(new com.nexora.nexorabackend.subscription.domain.queries.GetSubscriptions())
                .stream()
                .map(sub -> new SubscriptionResource(
                        sub.getId(),
                        sub.getPlanName(),
                        sub.getPrice(),
                        sub.isEnable(),
                        sub.getUserId()
                ))
                .toList();

        return ResponseEntity.ok(subscriptions);
    }

}
