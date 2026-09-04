package com.uzima.dtos;

import com.uzima.enums.ClinicalReportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClinicalReportRequest {

    @NotNull
    private Long visitId;

    private ClinicalReportType reportType;

    private LocalDateTime reportDate;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 2000)
    private String findings;

    @Size(max = 1000)
    private String conclusion;

    @Size(max = 1000)
    private String recommendations;

    @Size(max = 1000)
    private String notes;
}
