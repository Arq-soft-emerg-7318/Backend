package com.nexora.nexorabackend.subscription.interfaces.rest.resources;

import java.sql.Date;

public record CreateSubscriptionResource (
        String planName,
        Float price,
        Boolean enable,
        Long userId,
        Date starDate,
        Date endDate
){}