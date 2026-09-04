package com.uzima.controllers;

import com.uzima.dtos.ClinicalReportRequest;
import com.uzima.dtos.ClinicalReportResponse;
import com.uzima.enums.ClinicalReportType;
import com.uzima.services.ClinicalReportService;
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
@RequestMapping("/api/clinical-reports")
@RequiredArgsConstructor
public class ClinicalReportController {

    private final ClinicalReportService clinicalReportService;

    @PostMapping
    public ResponseEntity<ClinicalReportResponse> createClinicalReport(
            @Valid @RequestBody ClinicalReportRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(clinicalReportService.createClinicalReport(request));
    }

    @GetMapping("/{id}")
    public ClinicalReportResponse getClinicalReport(@PathVariable Long id) {
        return clinicalReportService.getClinicalReport(id);
    }

    @GetMapping
    public List<ClinicalReportResponse> getAllClinicalReports() {
        return clinicalReportService.getAllClinicalReports();
    }

    @PutMapping("/{id}")
    public ClinicalReportResponse updateClinicalReport(
            @PathVariable Long id,
            @Valid @RequestBody ClinicalReportRequest request) {

        return clinicalReportService.updateClinicalReport(id, request);
    }

    @DeleteMapping("/{id}")
    public ClinicalReportResponse deleteClinicalReport(@PathVariable Long id) {
        return clinicalReportService.deleteClinicalReport(id);
    }

    @GetMapping("/visit/{visitId}")
    public List<ClinicalReportResponse> getReportsByVisit(@PathVariable Long visitId) {
        return clinicalReportService.getReportsByVisit(visitId);
    }

    @GetMapping("/patient/{patientId}")
    public List<ClinicalReportResponse> getReportsByPatient(@PathVariable Long patientId) {
        return clinicalReportService.getReportsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<ClinicalReportResponse> getReportsByDoctor(@PathVariable Long doctorId) {
        return clinicalReportService.getReportsByDoctor(doctorId);
    }

    @GetMapping("/type/{reportType}")
    public List<ClinicalReportResponse> getReportsByType(@PathVariable ClinicalReportType reportType) {
        return clinicalReportService.getReportsByType(reportType);
    }
}
