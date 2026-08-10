package com.uzima.models;

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

@Table(name = "doctor")
public class Doctor extends Auditable {

    @Column(name = "doctor_number")
    private String doctorNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "deleted_flag")
    private Boolean deletedFlag = false;

    @ManyToOne
    @JoinColumn(name = "service_point_id", nullable = false)
    private ServicePoint servicePoint;

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patient;

}
