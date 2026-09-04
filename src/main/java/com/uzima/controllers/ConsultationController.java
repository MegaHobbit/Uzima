package com.uzima.controllers;

import com.uzima.dtos.ConsultationRequest;
import com.uzima.dtos.ConsultationResponse;
import com.uzima.services.ConsultationService;
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
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationResponse> createConsultation(
            @Valid @RequestBody ConsultationRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(consultationService.createConsultation(request));
    }

    @GetMapping("/{id}")
    public ConsultationResponse getConsultation(@PathVariable Long id) {
        return consultationService.getConsultation(id);
    }

    @GetMapping
    public List<ConsultationResponse> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @PutMapping("/{id}")
    public ConsultationResponse updateConsultation(
            @PathVariable Long id,
            @Valid @RequestBody ConsultationRequest request) {

        return consultationService.updateConsultation(id, request);
    }

    @DeleteMapping("/{id}")
    public ConsultationResponse deleteConsultation(@PathVariable Long id) {
        return consultationService.deleteConsultation(id);
    }

    @GetMapping("/visit/{visitId}")
    public ConsultationResponse getConsultationByVisit(@PathVariable Long visitId) {
        return consultationService.getConsultationByVisit(visitId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<ConsultationResponse> getConsultationsByDoctor(@PathVariable Long doctorId) {
        return consultationService.getConsultationsByDoctor(doctorId);
    }
}
