package com.uzima.dtos;

import com.uzima.enums.PrescriptionStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PrescriptionRequest {

    @NotNull
    private Long visitId;

    private PrescriptionStatus status;

    @Size(max = 1000)
    private String instructions;

    @Size(max = 1000)
    private String notes;

    @Valid
    @NotEmpty
    private List<PrescriptionItemRequest> items;
}
