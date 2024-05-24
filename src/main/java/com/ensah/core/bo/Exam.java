package com.ensah.core.bo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExam;
    private Date StartDate;
    private Date finPreuve;
    private Date finReel;
    private String preuve;
    private String pv;
    private String Rapport;


    //monitoring-exam
    @OneToMany(mappedBy = "exam")
    private List<Monitoring> Monitorins;


    //element - exam
    @ManyToOne
    @JoinColumn(name = "id_element")
    private Educationalelement element;


    //session-exam
    @Enumerated(EnumType.STRING)
    private Session session;


    //semester-exam
    @Enumerated(EnumType.STRING)
    private Semester semester;


    //examtype-exam
    @Enumerated(EnumType.STRING)
    private ExamType examType;
}
