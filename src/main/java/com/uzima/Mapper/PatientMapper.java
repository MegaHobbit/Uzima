package com.uzima.Mapper;

import com.uzima.dtos.PatientRequest;
import com.uzima.dtos.PatientResponse;
import com.uzima.enums.Gender;
import com.uzima.models.Patient;
import lombok.Data;

@Data
public class PatientMapper {


    //From PatientRequest
    private Long patientId;
    private String patientFirstName;
    private String patientLastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    //    from foreign key
    private Long doctorId;

    //   from doctor
    private String doctorNumber;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorPhoneNumber;
    //   from service-point
    private String pointName;

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

    public static PatientResponse toResponse(Patient patient) {

        PatientResponse response = new PatientResponse();

        //Patient
        response.setPatientNumber(patient.getPatientNumber());
        response.setPatientFirstName(patient.getFirstName());
        response.setPatientLastName(patient.getLastName());

        //Doctor
        response.setDoctorFirstName(patient.getDoctor().getFirstName());
        response.setDoctorLastName(patient.getDoctor().getLastName());
        response.setDoctorPhoneNumber(patient.getDoctor().getPhoneNumber());
        response.setDoctorNumber(patient.getDoctor().getDoctorNumber());

        //ServicePoint
        response.setPointName(patient.getDoctor().getServicePoint().getPointName());

        return response;
    }

    public static void updatePatient(Patient patient, PatientRequest patientRequest) {

        PatientResponse patientResponse = new PatientResponse();

        //Patient
        patient.setPatientNumber(patientRequest.getPatientNumber());
        patient.setPhoneNumber(patientRequest.getPhoneNumber());
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setEmail(patientRequest.getEmail());
        patient.setGender(patientRequest.getGender());

    }
}
