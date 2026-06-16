package com.Uzima.Model;

import com.Uzima.enums.LabStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "laboratory")

public class Lab extends BluePrint{

    private String laboratoryNumber;
    private String labName;
    private LabStatus status;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "laboratory", cascade = CascadeType.ALL)
    private List<LabTests> labTests = new ArrayList<>();

}
