package com.ensah.core.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "pgroup")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGroup;

    @Size(min = 5, max = 20, message= "Title must be between 5 and 20 characters")
    private String title;


    @Size(min = 10, max = 200, message= "Description must be between 10 and 200 characters")
    private String description;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Groups_Profs",
            joinColumns = @JoinColumn(name = "id_Group"),
            inverseJoinColumns = @JoinColumn(name = "id_Professor"))
    private List<Professor> professors=new ArrayList<>();

}
