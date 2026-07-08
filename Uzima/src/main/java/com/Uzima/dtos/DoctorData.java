package com.Uzima.dtos;

import com.Uzima.models.Doctor;
import lombok.Data;

@Data
public class DoctorData {

    private String doctorNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;

    public static DoctorData toData(Doctor doctor) {

        DoctorData doctorData = new DoctorData();
        doctorData.setDoctorNumber(doctor.getDoctorNumber());
        doctorData.setFirstName(doctor.getFirstName());
        doctorData.setLastName(doctor.getLastName());
        doctorData.setPhoneNumber(doctor.getPhoneNumber());

        return doctorData;
    }

    public static Doctor fromData(DoctorData doctorData) {

        Doctor doctor = new Doctor();

        doctor.setDoctorNumber(doctorData.getDoctorNumber());
        doctor.setFirstName(doctorData.getFirstName());
        doctor.setLastName(doctorData.getLastName());
        doctor.setPhoneNumber(doctorData.getPhoneNumber());

        return doctor;
    }
}
