package com.ensah.core.bo;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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

    @NotBlank
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[A-Z]{2}\\d{6}", message = "CIN must start with 2 capital letters followed by 6 numbers")
    private String cin;

    @NotBlank @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b", message = "Invalid email format")
    private String email;

    @Override
    public String toString() {
        return "Person [idPerson=" + idPerson + ", Firstname=" + firstName + ", Lastname=" + lastName + ", cin=" + cin + "]";
    }
}
