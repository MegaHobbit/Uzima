package com.uzima.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicationRequest {

    @NotBlank
    @Size(max = 100)
    private String medicationName;

    @Size(max = 100)
    private String genericName;

    @Size(max = 50)
    private String dosageForm;

    @Size(max = 50)
    private String strength;

    @Size(max = 500)
    private String description;

    private Boolean activeFlag;
}
