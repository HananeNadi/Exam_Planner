package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Getter
@Setter
@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idExam;


    private String startTime;


    private String endTime;
    private String duration;
    private String reelDuration;
    private String preuve;
    private String pv;
    private String Rapport;


    @JsonIgnore
    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Monitoring> monitorins;



//    @JsonIgnore (mni anbghiw ncrew monitoring and get the element id this will null -->error)
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
