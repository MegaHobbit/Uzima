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

    public static PatientData toData(Patient patient) {

        PatientData patientData = new PatientData();
         patientData.setPatientNumber(patient.getPatientNumber());
         patientData.setFirstName(patient.getFirstName());
         patientData.setLastName(patient.getLastName());
         patientData.setEmail(patient.getEmail());
         patientData.setPhoneNumber(patient.getPhoneNumber());
         patientData.setGender(patient.getGender());

         return patientData;
    }

    public static Patient fromData (PatientData patientData) {

        Patient patient = new Patient();
        patient.setPatientNumber(patientData.getPatientNumber());
        patient.setFirstName(patientData.getFirstName());
        patient.setLastName(patientData.getLastName());
        patient.setEmail(patientData.getEmail());
        patient.setPhoneNumber(patientData.getPhoneNumber());
        patient.setGender(patientData.getGender());

        return patient;
    }
}
