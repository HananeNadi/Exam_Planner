package com.ensah.core.bo;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPerson;
    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String firstName;
    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String lastName;

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String type;

    @Column(unique = true)
    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String cin;

    @Override
    public String toString() {
        return "Person [idPerson=" + idPerson + ", Firstname=" + firstName + ", Lastname=" + lastName + ", cin=" + cin + "]";
    }
}
