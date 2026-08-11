package com.uzima.Mapper;

import com.uzima.dtos.DoctorRequest;
import com.uzima.dtos.DoctorResponse;
import com.uzima.models.Doctor;
import lombok.Data;

@Data
public class DoctorMapper {

    private Long id;
    private String doctorNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;


    //    from foreign key
    private Long servicePointId;
    private String servicePointName;

    public static DoctorResponse toResponse(Doctor doctor) {

        DoctorResponse response = new DoctorResponse();
        response.setDoctorNumber(doctor.getDoctorNumber());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setPhoneNumber(doctor.getPhoneNumber());
        response.setServicePointId(doctor.getServicePoint().getId());
        response.setServicePointName(doctor.getServicePoint().getPointName());
        response.setId(doctor.getId());

        return response;
    }

    public static Doctor fromRequest(DoctorRequest request) {

        Doctor doctor = new Doctor();

        doctor.setDoctorNumber(request.getDoctorNumber());
        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setPhoneNumber(request.getPhoneNumber());

        return doctor;
    }
}
