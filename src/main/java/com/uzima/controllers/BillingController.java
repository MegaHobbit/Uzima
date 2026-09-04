package com.uzima.controllers;

import com.uzima.dtos.BillingRequest;
import com.uzima.dtos.BillingResponse;
import com.uzima.enums.BillingStatus;
import com.uzima.services.BillingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
@RequiredArgsConstructor
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public ResponseEntity<BillingResponse> createBilling(@Valid @RequestBody BillingRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createBilling(request));
    }

    @GetMapping("/{id}")
    public BillingResponse getBilling(@PathVariable Long id) {
        return billingService.getBilling(id);
    }

    @GetMapping
    public List<BillingResponse> getAllBillings() {
        return billingService.getAllBillings();
    }

    @PutMapping("/{id}")
    public BillingResponse updateBilling(@PathVariable Long id, @Valid @RequestBody BillingRequest request) {
        return billingService.updateBilling(id, request);
    }

    @DeleteMapping("/{id}")
    public BillingResponse deleteBilling(@PathVariable Long id) {
        return billingService.deleteBilling(id);
    }

    @GetMapping("/visit/{visitId}")
    public List<BillingResponse> getBillingsByVisit(@PathVariable Long visitId) {
        return billingService.getBillingsByVisit(visitId);
    }

    @GetMapping("/patient/{patientId}")
    public List<BillingResponse> getBillingsByPatient(@PathVariable Long patientId) {
        return billingService.getBillingsByPatient(patientId);
    }

    @GetMapping("/status/{status}")
    public List<BillingResponse> getBillingsByStatus(@PathVariable BillingStatus status) {
        return billingService.getBillingsByStatus(status);
    }
}
