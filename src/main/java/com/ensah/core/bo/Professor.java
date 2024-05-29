package com.ensah.core.bo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@PrimaryKeyJoinColumn(name = "IdProfessor")
@Getter
@Setter
@RequiredArgsConstructor
public class Professor extends Person {


    private String speciality;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"professors", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_Departement")
    private Departement departement;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"professors", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_sector")
    private Sector sector;



    @ManyToMany(mappedBy = "professors",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Group> groups;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "Monitors_Profs",
            joinColumns = @JoinColumn(name = "id_Professor"),
            inverseJoinColumns = @JoinColumn(name = "id_Monitor"))
    @JsonIgnore
    private Set<Monitoring> monitors = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "coordinator",fetch = FetchType.LAZY)
    private Set<Monitoring> monitorins;


    @JsonIgnore
    @OneToMany(mappedBy = "professor",fetch = FetchType.LAZY)
    private Set<Educationalelement> elements;



}
