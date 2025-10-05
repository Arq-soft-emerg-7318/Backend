package com.nexora.nexorabackend.subscription.domain.aggregates;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import com.nexora.nexorabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.nexora.nexorabackend.subscription.domain.commands.CreateSubscriptionCommand;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Subscription extends AuditableAbstractAggregateRoot<Subscription> {

   @Column(nullable = false)
   private String  planName;
    
   @Column(nullable = false)
   private Float price;
   @Column(nullable = false)
    private LocalDate starDate;
   @Column(nullable = false)
    private LocalDate endDate; 


   @Column(nullable = false)
   private boolean enable;

    @Column(nullable = false)
    private Long userId;
   
    public Subscription(CreateSubscriptionCommand command){
        this.planName = command.planName();
        this.price = command.price();
        this.enable = command.enable();
        this.userId = command.userId();
        this.starDate = command.startDate().toLocalDate();
        this.endDate = command.endDate().toLocalDate();

    }

    public Subscription(){}

    /* 
    public void updatePlan(UpdateSubscriptionCommand command){
        this.starDate = command.starDate;
        this.endDate = command.endDate;
        this.enable = command.enable;
    }
*/
}
