package com.ensah.core.bo;

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

    @OneToMany(mappedBy = "sector")
    private List<Professor> professors;
}
