package com.uzima.dtos;

import com.uzima.enums.BillingStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BillingRequest {

    @NotNull
    private Long visitId;

    private BillingStatus status;

    @Size(max = 1000)
    private String notes;

    @Valid
    @NotEmpty
    private List<BillingItemRequest> items;
}
