package com.uzima.dtos;

import com.uzima.models.Doctor;
import lombok.Data;

@Data
public class DoctorData {

    private Long id;
    private String doctorNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;


//    from foreign key
    private Long servicePointId;
    private String servicePointName;

    public static DoctorData toData(Doctor doctor) {

        DoctorData doctorData = new DoctorData();
        doctorData.setDoctorNumber(doctor.getDoctorNumber());
        doctorData.setFirstName(doctor.getFirstName());
        doctorData.setLastName(doctor.getLastName());
        doctorData.setPhoneNumber(doctor.getPhoneNumber());
        doctorData.setServicePointId(doctor.getServicePoint().getId());
        doctorData.setServicePointName(doctor.getServicePoint().getPointName());
        doctorData.setId(doctor.getId());

        return doctorData;
    }
}
