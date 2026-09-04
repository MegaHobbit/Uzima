package com.uzima.dtos;

import com.uzima.enums.ClinicalReportType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClinicalReportResponse {

    private Long id;
    private String reportNumber;
    private Long visitId;
    private String visitNumber;
    private Long patientId;
    private Long doctorId;
    private ClinicalReportType reportType;
    private LocalDateTime reportDate;
    private String title;
    private String findings;
    private String conclusion;
    private String recommendations;
    private String notes;
    private Boolean deletedFlag;
}
