package com.uzima.dtos;

import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import lombok.Data;

@Data

public class PatientDetails {

    //   from patient
    private String patientNumber;
    private String patientFirstName;
    private String PatientLastName;

    //   from doctor
    private String doctorNumber;
    private String doctorFirstName;
    private String doctorLastName;
    private String phoneNumber;

    //   from service-point
    private String pointName;


    public static PatientDetails toData(Patient patient, Doctor doctor, ServicePoint servicePoint) {

        PatientDetails patientDetails = new PatientDetails();

        patientDetails.setPatientNumber(patient.getPatientNumber());
        patientDetails.setPatientFirstName(patient.getFirstName());
        patientDetails.setPatientLastName(patient.getLastName());

        patientDetails.setDoctorFirstName(doctor.getFirstName());
        patientDetails.setDoctorLastName(doctor.getLastName());
        patientDetails.setPhoneNumber(doctor.getPhoneNumber());
        patientDetails.setDoctorNumber(doctor.getDoctorNumber());

        patientDetails.setPointName(servicePoint.getPointName());

        return patientDetails;
    }

}
