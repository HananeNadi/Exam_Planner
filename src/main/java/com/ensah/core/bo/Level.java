package com.ensah.core.bo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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


    @JsonIgnore
    @OneToMany(mappedBy = "level",fetch = FetchType.LAZY)
    private List<Educationalelement> elemnts;
}
