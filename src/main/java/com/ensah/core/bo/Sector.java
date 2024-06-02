package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSector;

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    @Size(min = 5,message= "Room Name must be between 5 and 20 characters")
    private String Title;


    @JsonIgnore
    @OneToMany(mappedBy = "sector",fetch = FetchType.LAZY)
    private Set<Professor> professors;
}
