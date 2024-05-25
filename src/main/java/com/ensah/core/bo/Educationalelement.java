package com.ensah.core.bo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Educationalelement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idElement;
    private String title;

    @ManyToOne
    @JoinColumn(name = "id_level")
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "element_type")
    private ElementType elementType;



    //element - professor
    @ManyToOne
    @JoinColumn(name = "idprofessor")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "idcoordinator")
    private Professor coordinator;


    //element - exam
    @OneToMany(mappedBy = "element",cascade = CascadeType.ALL)
    private List<Exam> exams;


}
