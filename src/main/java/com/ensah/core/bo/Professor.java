package com.ensah.core.bo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

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
    private List<Group> groups;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "Monitors_Profs",
            joinColumns = @JoinColumn(name = "id_Professor"),
            inverseJoinColumns = @JoinColumn(name = "id_Monitor"))
    @JsonIgnore
    private List<Monitoring> monitors = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "coordinator",fetch = FetchType.LAZY)
    private List<Monitoring> monitorins;


    @JsonIgnore
    @OneToMany(mappedBy = "professor",fetch = FetchType.LAZY)
    private List<Educationalelement> elements;



}
