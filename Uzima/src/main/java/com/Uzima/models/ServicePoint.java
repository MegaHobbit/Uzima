package com.Uzima.models;


import com.Uzima.enums.PointName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "service_point")
public class ServicePoint extends Auditable{

    @Column(name = "point_name")
    @Enumerated(EnumType.STRING)
    private PointName servicePoint;

    @OneToMany(mappedBy = "servicePoint")
    private List<Patient> patients;

    @OneToMany(mappedBy = "servicePoint")
    private List<Doctor> doctors;

}


