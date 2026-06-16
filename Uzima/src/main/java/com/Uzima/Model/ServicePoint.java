package com.Uzima.Model;


import com.Uzima.enums.ServicePointStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "servicePoint")

public class ServicePoint extends BluePrint{

    private String  servicePointId;
    private String servicePointName;
    private String description;
    private ServicePointStatus status;

    @OneToMany(mappedBy = "ServicePoint", cascade = CascadeType.ALL)
    private List<Patient> patients = new ArrayList<>();

}
