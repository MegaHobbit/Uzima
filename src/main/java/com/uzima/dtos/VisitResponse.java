package com.uzima.dtos;

import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitResponse {

    private Long id;
    private String visitNumber;
    private LocalDateTime visitDateTime;
    private VisitType visitType;
    private VisitStatus visitStatus;
    private String chiefComplaint;
    private String notes;
    private Boolean deletedFlag;

    private Long patientId;
    private String patientNumber;
    private String patientName;

    private Long doctorId;
    private String doctorNumber;
    private String doctorName;

    private Long servicePointId;
    private String servicePointName;

    private Long appointmentId;
}
