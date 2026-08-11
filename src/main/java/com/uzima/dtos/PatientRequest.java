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


}
