package com.ensah.core.bo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepartement;

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    @Size(min = 5, message= "Title must be between 5 and 20 characters")
    private String title;


    @JsonIgnore
    @OneToMany(mappedBy = "departement",fetch = FetchType.LAZY)
    private Set<Professor> professors;
}
