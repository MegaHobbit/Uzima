package com.uzima.controllers;

import com.uzima.dtos.PrescriptionRequest;
import com.uzima.dtos.PrescriptionResponse;
import com.uzima.enums.PrescriptionStatus;
import com.uzima.services.PrescriptionService;
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
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<PrescriptionResponse> createPrescription(
            @Valid @RequestBody PrescriptionRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(prescriptionService.createPrescription(request));
    }

    @GetMapping("/{id}")
    public PrescriptionResponse getPrescription(@PathVariable Long id) {
        return prescriptionService.getPrescription(id);
    }

    @GetMapping
    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @PutMapping("/{id}")
    public PrescriptionResponse updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionRequest request) {

        return prescriptionService.updatePrescription(id, request);
    }

    @DeleteMapping("/{id}")
    public PrescriptionResponse deletePrescription(@PathVariable Long id) {
        return prescriptionService.deletePrescription(id);
    }

    @GetMapping("/visit/{visitId}")
    public List<PrescriptionResponse> getPrescriptionsByVisit(@PathVariable Long visitId) {
        return prescriptionService.getPrescriptionsByVisit(visitId);
    }

    @GetMapping("/patient/{patientId}")
    public List<PrescriptionResponse> getPrescriptionsByPatient(@PathVariable Long patientId) {
        return prescriptionService.getPrescriptionsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<PrescriptionResponse> getPrescriptionsByDoctor(@PathVariable Long doctorId) {
        return prescriptionService.getPrescriptionsByDoctor(doctorId);
    }

    @GetMapping("/status/{status}")
    public List<PrescriptionResponse> getPrescriptionsByStatus(@PathVariable PrescriptionStatus status) {
        return prescriptionService.getPrescriptionsByStatus(status);
    }
}
