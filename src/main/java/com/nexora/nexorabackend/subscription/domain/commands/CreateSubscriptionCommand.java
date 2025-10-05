package com.nexora.nexorabackend.subscription.domain.commands;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;


public record CreateSubscriptionCommand (String planName, Float price, Boolean enable,Long userId, Date startDate, Date endDate){

    public CreateSubscriptionCommand{
        Objects.requireNonNull(planName,"Plan name cannot be null");
        Objects.requireNonNull(price, "Price cannot be null");
        Objects.requireNonNull(enable, "Enable cannot be null");
        Objects.requireNonNull(userId, "User id cannot be null");
    }

 


}