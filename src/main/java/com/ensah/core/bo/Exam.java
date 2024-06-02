package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String startTime;

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String endTime;

    private String duration;
    private String reelDuration;
    private String preuve;
    private String pv;
    private String Rapport;


    @JsonIgnore
    @OneToMany(mappedBy = "exam")
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
