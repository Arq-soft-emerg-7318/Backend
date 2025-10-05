package com.nexora.nexorabackend.subscription.interfaces.rest.transform;

import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;
import com.nexora.nexorabackend.subscription.interfaces.rest.resources.CreateSubscriptionResource;

public class CreateSubscriptionCommandFromResourceAssembler {

    public static CreateSubscriptionCommand toCommandFromResource (CreateSubscriptionResource resource, Long userId){
        return new CreateSubscriptionCommand(
            resource.planName(),
            resource.price(),
            resource.enable(),
            userId,
            resource.starDate(),
            resource.endDate()
            
        );
    }
    
}
