package com.uzima.dtos;

import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitRequest {

    private LocalDateTime visitDateTime;

    private VisitType visitType;

    private VisitStatus visitStatus;

    @Size(max = 500)
    private String chiefComplaint;

    @Size(max = 1000)
    private String notes;

    private Long patientId;

    private Long doctorId;

    private Long servicePointId;

    private Long appointmentId;
}
