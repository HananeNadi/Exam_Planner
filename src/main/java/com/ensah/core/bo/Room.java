package com.ensah.core.bo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
public class  Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRoom;
    private String nameRoom;

    private String type;

    private String place;
    @Min(value = 10) @Max(value = 300)
    private Long size;

    @OneToMany(mappedBy = "room")
    private List<Monitoring> Monitorins;
}
