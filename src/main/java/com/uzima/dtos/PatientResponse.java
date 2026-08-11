package com.uzima.dtos;

import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import lombok.Data;

@Data

public class PatientResponse {

    //   from patient
    private String patientNumber;
    private String patientFirstName;
    private String PatientLastName;

    //   from doctor
    private String doctorNumber;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorPhoneNumber;

    //   from service-point
    private String pointName;



}
