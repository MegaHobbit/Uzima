package com.uzima.dtos;

import com.uzima.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {

    private LocalDateTime appointmentDateTime = LocalDateTime.now();
    private AppointmentStatus status;
    private String reason;
    private String notes;
    private String appointmentType;

    //Foreign keys
    private Long patientId;
    private Long doctorId;
    private Long servicePointId;

}
