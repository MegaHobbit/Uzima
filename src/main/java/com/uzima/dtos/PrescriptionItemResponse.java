package com.uzima.dtos;

import com.uzima.enums.MedicationRoute;
import lombok.Data;

@Data
public class PrescriptionItemResponse {

    private Long id;
    private Long medicationId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String duration;
    private Integer quantity;
    private MedicationRoute route;
    private String instructions;
}
