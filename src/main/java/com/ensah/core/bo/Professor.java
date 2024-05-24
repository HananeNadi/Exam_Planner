package com.ensah.core.bo;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "IdProfessor")
@Getter
@Setter
public class Professor extends Person {


    private String speciality;



    @ManyToOne
    @JoinColumn(name = "id_Departement")
    private Departement departement;


    @ManyToOne
    @JoinColumn(name = "id_sector")
    private Sector sector;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Groups_Profs",
            joinColumns = @JoinColumn(name = "id_Professor"),
            inverseJoinColumns = @JoinColumn(name = "id_Group"))
    private List<Group> groups = new ArrayList<>();



    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Monitors_Profs",
            joinColumns = @JoinColumn(name = "id_Professor"),
            inverseJoinColumns = @JoinColumn(name = "id_Monitor"))
    private List<Monitoring> monitors = new ArrayList<>();


    @OneToMany(mappedBy = "cordinator")
    private List<Monitoring> Monitorins;

    @OneToMany(mappedBy = "professor")
    private List<Educationalelement> elements;



}
