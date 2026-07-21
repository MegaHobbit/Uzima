package com.uzima.dtos;

import com.uzima.enums.Gender;
import com.uzima.models.Patient;
import lombok.Data;

@Data

public class PatientRequest {

    private String patientNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;

    //    from foreign key
    private Long doctorId;

    public static Patient fromRequest(PatientRequest patientRequest) {

        Patient patient = new Patient();
        patient.setPatientNumber(patientRequest.getPatientNumber());
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setEmail(patientRequest.getEmail());
        patient.setPhoneNumber(patientRequest.getPhoneNumber());
        patient.setGender(patientRequest.getGender());

        return patient;
    }


}
