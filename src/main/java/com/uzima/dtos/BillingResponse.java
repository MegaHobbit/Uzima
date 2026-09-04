package com.uzima.dtos;

import com.uzima.enums.BillingStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class BillingResponse {

    private Long id;
    private String billNumber;
    private Long visitId;
    private String visitNumber;
    private Long patientId;
    private LocalDateTime billingDate;
    private BillingStatus status;
    private BigDecimal totalAmount;
    private String notes;
    private Boolean deletedFlag;
    private List<BillingItemResponse> items;
}
