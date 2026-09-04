package com.uzima.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ConsultationResponse {

    private Long id;
    private Long visitId;
    private String visitNumber;
    private Long patientId;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime consultationDateTime;
    private String chiefComplaint;
    private String historyOfPresentIllness;
    private String examinationFindings;
    private String assessment;
    private String diagnosis;
    private String treatmentPlan;
    private String notes;
    private Boolean deletedFlag;
}
