package com.uzima.dtos;

import com.uzima.enums.Gender;
import com.uzima.models.Patient;
import lombok.Data;

@Data
public class PatientData {


    private String patientNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Long id;

    public static PatientData toData(Patient patient) {

        PatientData patientData = new PatientData();
        patientData.setPatientNumber(patient.getPatientNumber());
        patientData.setFirstName(patient.getFirstName());
        patientData.setLastName(patient.getLastName());
        patientData.setEmail(patient.getEmail());
        patientData.setPhoneNumber(patient.getPhoneNumber());
        patientData.setGender(patient.getGender());
        patientData.setId(patient.getId());

        return patientData;
    }
}
