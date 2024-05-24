package com.ensah.core.bo;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "idAdministrator ")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Administrator extends Person{

    @NotNull
    @NotBlank(message = "This field should not be empty !")
    private String grade;

    @OneToMany(mappedBy = "administrator")
    private List<Monitoring> Monitorins;

}
