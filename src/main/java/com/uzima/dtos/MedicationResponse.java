package com.uzima.dtos;

import lombok.Data;

@Data
public class MedicationResponse {

    private Long id;
    private String medicationName;
    private String genericName;
    private String dosageForm;
    private String strength;
    private String description;
    private Boolean activeFlag;
    private Boolean deletedFlag;
}
