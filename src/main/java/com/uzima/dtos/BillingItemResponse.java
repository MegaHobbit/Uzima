package com.uzima.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillingItemResponse {

    private Long id;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal amount;
}
