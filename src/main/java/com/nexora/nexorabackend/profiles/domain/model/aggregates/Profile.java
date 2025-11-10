package com.nexora.nexorabackend.profiles.domain.model.aggregates;

import com.nexora.nexorabackend.iam.domain.model.aggregates.User;
import com.nexora.nexorabackend.profiles.domain.model.commands.CreateProfileCommand;
import com.nexora.nexorabackend.profiles.domain.model.commands.UpdateProfileCommand;
import com.nexora.nexorabackend.profiles.domain.model.valueobjects.PersonName;
import com.nexora.nexorabackend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Profile extends AuditableAbstractAggregateRoot<Profile> {
    @Embedded
    private PersonName name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String direction;

    @Column(nullable = false)
    private String documentNumber;

    @Column(nullable = false)
    private String documentType;

    @JoinColumn(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String phone;

    public Profile(PersonName name, String email, String direction, String documentNumber, String documentType, Long userId, String phone) {
        this.name = name;
        this.email = email;
        this.direction = direction;
        this.documentNumber = documentNumber;
        this.documentType = documentType;
        this.phone = phone;
        this.userId = userId;
    }

    public Profile(CreateProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = command.email();
        this.direction = command.direction();
        this.documentNumber = command.documentNumber();
        this.documentType = command.documentType();
        this.phone = command.phone();
        this.userId = command.userId();
    }

    public Profile() {}

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public String getFullName() { return name.getFullName(); }

    public String getFirstName() {
        return name.getFirstName();
    }
    public  String getLastName() {
        return name.getLastName();
    }

    public void update(UpdateProfileCommand command) {
        this.name = new PersonName(command.firstName(), command.lastName());
        this.email = command.email();
        this.documentNumber = command.documentNumber();
        this.documentType = command.documentType();
        this.phone = command.phone();
        this.direction = command.direction();
    }
}
