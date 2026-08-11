package com.uzima.dtos;

import lombok.Data;

@Data
public class DoctorResponse {

    private Long id;
    private String doctorNumber;
    private String firstName;
    private String lastName;
    private String phoneNumber;


    //    from foreign key
    private Long servicePointId;
    private String servicePointName;
}
