package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "IdProfessor")
@Getter
@Setter
@RequiredArgsConstructor
public class Professor extends Person {


    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"professors", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_Departement")
    private Departement departement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"professors", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_sector")
    private Sector sector;

    @JsonIgnore
    @ManyToMany(mappedBy = "professors", fetch = FetchType.LAZY)
    private Set<Group> groups;

    @JsonIgnore
    @ManyToMany(mappedBy = "professors", fetch = FetchType.LAZY)
    private Set<Monitoring> monitorings;

    @JsonIgnore
    @OneToMany(mappedBy = "coordinator",fetch = FetchType.LAZY)
    private Set<Monitoring> monitorins;

    @JsonIgnore
    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private Set<Educationalelement> elements;
}
