package com.uzima.dtos;

import com.uzima.models.Doctor;
import lombok.Data;

@Data

public class DoctorRequest {

    private String doctorNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    //    from foreign key
    private Long servicePointId;

    public static Doctor fromRequest(DoctorRequest doctorRequest) {

        Doctor doctor = new Doctor();

        doctor.setDoctorNumber(doctorRequest.getDoctorNumber());
        doctor.setFirstName(doctorRequest.getFirstName());
        doctor.setLastName(doctorRequest.getLastName());
        doctor.setPhoneNumber(doctorRequest.getPhoneNumber());

        return doctor;
    }
}
