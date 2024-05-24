package com.ensah.core.bo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLevel;
    private String title;


    @OneToMany(mappedBy = "level")
    private List<Educationalelement> elemnts;
}
