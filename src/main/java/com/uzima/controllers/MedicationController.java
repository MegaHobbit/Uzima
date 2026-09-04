package com.uzima.controllers;

import com.uzima.dtos.MedicationRequest;
import com.uzima.dtos.MedicationResponse;
import com.uzima.services.MedicationService;
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
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping
    public ResponseEntity<MedicationResponse> createMedication(@Valid @RequestBody MedicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicationService.createMedication(request));
    }

    @GetMapping("/{id}")
    public MedicationResponse getMedication(@PathVariable Long id) {
        return medicationService.getMedication(id);
    }

    @GetMapping
    public List<MedicationResponse> getAllMedications() {
        return medicationService.getAllMedications();
    }

    @GetMapping("/active")
    public List<MedicationResponse> getActiveMedications() {
        return medicationService.getActiveMedications();
    }

    @PutMapping("/{id}")
    public MedicationResponse updateMedication(@PathVariable Long id, @Valid @RequestBody MedicationRequest request) {
        return medicationService.updateMedication(id, request);
    }

    @DeleteMapping("/{id}")
    public MedicationResponse deleteMedication(@PathVariable Long id) {
        return medicationService.deleteMedication(id);
    }
}
