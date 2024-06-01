package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Educationalelement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idElement;

    private String title;

    // Element - Level
    @ManyToOne
    @JoinColumn(name = "id_level")
    private Level level;

    // Modul/Element
    @Enumerated(EnumType.STRING)
    @Column(name = "element_type")
    private ElementType elementType;

    // Element - Professor
    @ManyToOne
    @JoinColumn(name = "idprofessor")
    private Professor professor;

    // Surveillance - Coordinator
    @ManyToOne
    @JoinColumn(name = "idcoordinator")
    private Professor coordinator;


    // Element - Exam
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Exam> exams;
}
