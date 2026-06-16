package com.Uzima.Model;

import com.Uzima.enums.DoctorStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "doctors")
public class Doctors extends Person{

    @Column(name = "doctor_reg_number")
    private String doctorRegNumber;

    @Column(name = "doctor_acc_number")
    private String doctorAccNumber;

    @Column(name = "status")
    private DoctorStatus status;

    @OneToMany(mappedBy = "doctors", cascade = CascadeType.ALL)
    private List<Patient> patients = new ArrayList<>();
}