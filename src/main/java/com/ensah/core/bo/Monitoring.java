package com.ensah.core.bo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Monitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMonitor;

    @ManyToMany(mappedBy = "monitors")
    private List<Professor> professors=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_Cordinator")
    private Professor cordinator;

    @ManyToOne
    @JoinColumn(name = "id_Administator")
    private Administrator administrator;

    @ManyToOne
    @JoinColumn(name = "id_Room")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "id_Exam")
    private Exam exam;
}
