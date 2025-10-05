package com.nexora.nexorabackend.subscription.interfaces.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

@RestController
@RequestMapping(value= "/api/v1/subscriptions", produces =  MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Subscriptions", description = "Subscription Management Endpoints")
@PreAuthorize("isAuthenticated()")
public class SubscriptionController {

    private final SubscriptionCommandService subscriptionCommandService;
    // private final SubscriptionQueryService subscriptionQueryService;

    public SubscriptionController(SubscriptionCommandService subscriptionCommandService, SubscriptionQueryService subscriptionQueryService) {
        this.subscriptionCommandService = subscriptionCommandService;
      //  this.subscriptionQueryService = subscriptionQueryService;
    }
@PostMapping
public ResponseEntity<SubscriptionResource> createSubscription(@RequestBody CreateSubscriptionResource resource) {
    System.out.println("➡️ [1] Entrando a createSubscription...");
    System.out.println("📦 Payload recibido: " + resource);

    try {
        // 1️⃣ Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("⚠️ [2] Authentication es null");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        System.out.println("👤 [2] Authentication: " + authentication);
        System.out.println("🔍 [2.1] Principal: " + authentication.getPrincipal());

        if (!(authentication.getPrincipal() instanceof UserDetailsImpl)) {
            System.out.println("⚠️ [2.2] El principal no es una instancia de UserDetailsImpl");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();
        System.out.println("✅ [3] ID de usuario autenticado: " + userId);

        // 2️⃣ Crear el comando
        CreateSubscriptionCommand createSubscriptionCommand =
                CreateSubscriptionCommandFromResourceAssembler.toCommandFromResource(resource, userId);

        System.out.println("🛠️ [4] Comando creado: " + createSubscriptionCommand);

        // 3️⃣ Ejecutar el servicio
        var subscription = subscriptionCommandService.handle(createSubscriptionCommand);
        System.out.println("🧩 [5] Resultado de handle(): " + subscription);

        if (subscription.isPresent()) {
            var sub = subscription.get();
            System.out.println("🎯 [6] Suscripción creada: " + sub);

            var subscriptionResource = new SubscriptionResource(
                    sub.getId(),
                    sub.getPlanName(),
                    sub.getPrice(),
                    sub.isEnable(),
                    sub.getUserId()
            );

            System.out.println("📤 [7] Respuesta construida correctamente.");
            return ResponseEntity.ok(subscriptionResource);
        }

        System.out.println("⚠️ [8] subscription.isPresent() == false — No se creó la suscripción");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    } catch (Exception e) {
        System.out.println("❌ [9] Excepción en createSubscription: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}

}