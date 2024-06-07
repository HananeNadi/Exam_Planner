package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank(message = "This field should not be empty !")
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
    @JsonIgnore
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL)
    private Set<Exam> exams;
}
