package com.ensah.core.bo;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDepartement;
    private String title;

    @OneToMany(mappedBy = "departement")
    private List<Professor> professors;
}
