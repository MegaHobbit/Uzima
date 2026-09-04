package com.uzima.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationRequest {

    @NotNull
    private Long visitId;

    private Long doctorId;

    private LocalDateTime consultationDateTime;

    @Size(max = 500)
    private String chiefComplaint;

    @Size(max = 1000)
    private String historyOfPresentIllness;

    @Size(max = 1000)
    private String examinationFindings;

    @Size(max = 1000)
    private String assessment;

    @Size(max = 500)
    private String diagnosis;

    @Size(max = 1000)
    private String treatmentPlan;

    @Size(max = 1000)
    private String notes;
}
