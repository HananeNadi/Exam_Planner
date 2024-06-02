package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Monitoring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMonitor;

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String dateExam;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Monitors_Profs",
            joinColumns = @JoinColumn(name = "id_Monitoring"),
            inverseJoinColumns = @JoinColumn(name = "id_Professor"))
    private Set<Professor> professors=new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "id_Cordinator")
    @JsonIgnoreProperties({"monitorins", "hibernateLazyInitializer", "handler"})
    private Professor coordinator;

    @ManyToOne
    @JoinColumn(name = "id_Administator")
    @JsonIgnoreProperties({"monitorins", "hibernateLazyInitializer", "handler"})
    private Administrator administrator;

    @ManyToOne
    @JsonIgnoreProperties({"monitorins", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_Room")
    private Room room;

    @ManyToOne
    @JsonIgnoreProperties({"monitorins", "hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "id_Exam")
    private Exam exam;
}
