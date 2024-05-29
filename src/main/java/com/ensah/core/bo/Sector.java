package com.ensah.core.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSector;
    private String Title;


    @JsonIgnore
    @OneToMany(mappedBy = "sector",fetch = FetchType.LAZY)
    private List<Professor> professors;
}
