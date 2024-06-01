package com.ensah.core.bo;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


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

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String type;

//    @NotBlank @Column(nullable = false, unique = true)
    private String cin;
//    @NotBlank @Column(nullable = false, unique = true)
    private String email;

    @Override
    public String toString() {
        return "Person [idPerson=" + idPerson + ", Firstname=" + firstName + ", Lastname=" + lastName + ", cin=" + cin + "]";
    }
}
