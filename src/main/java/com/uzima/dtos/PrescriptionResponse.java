package com.uzima.dtos;

import com.uzima.enums.PrescriptionStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PrescriptionResponse {

    private Long id;
    private String prescriptionNumber;
    private Long visitId;
    private String visitNumber;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime prescriptionDate;
    private PrescriptionStatus status;
    private String instructions;
    private String notes;
    private Boolean deletedFlag;
    private List<PrescriptionItemResponse> items;
}
