package com.uzima.dtos;

import com.uzima.enums.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentResponse {

    private Long id;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;
    private String reason;
    private String notes;
    private String appointmentType;

    //    from foreign key
    private Long servicePointId;
    private Long patientId;
    private Long doctorId;
}
