package com.uzima.dtos;

import com.uzima.enums.MedicationRoute;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PrescriptionItemRequest {

    @NotNull
    private Long medicationId;

    @NotBlank
    @Size(max = 100)
    private String dosage;

    @NotBlank
    @Size(max = 100)
    private String frequency;

    @NotBlank
    @Size(max = 100)
    private String duration;

    @NotNull
    @Positive
    private Integer quantity;

    private MedicationRoute route;

    @Size(max = 500)
    private String instructions;
}
