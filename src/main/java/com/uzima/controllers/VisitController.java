package com.uzima.controllers;

import com.uzima.dtos.VisitRequest;
import com.uzima.dtos.VisitResponse;
import com.uzima.enums.VisitStatus;
import com.uzima.services.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(@Valid @RequestBody VisitRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(visitService.createVisit(request));
    }

    @GetMapping("/{id}")
    public VisitResponse getVisit(@PathVariable Long id) {
        return visitService.getVisit(id);
    }

    @GetMapping
    public List<VisitResponse> getAllVisits() {
        return visitService.getAllVisits();
    }

    @PutMapping("/{id}")
    public VisitResponse updateVisit(@PathVariable Long id, @Valid @RequestBody VisitRequest request) {
        return visitService.updateVisit(id, request);
    }

    @DeleteMapping("/{id}")
    public VisitResponse deleteVisit(@PathVariable Long id) {
        return visitService.deleteVisit(id);
    }

    @GetMapping("/patient/{patientId}")
    public List<VisitResponse> getVisitsByPatient(@PathVariable Long patientId) {
        return visitService.findVisitsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<VisitResponse> getVisitsByDoctor(@PathVariable Long doctorId) {
        return visitService.findVisitsByDoctor(doctorId);
    }

    @GetMapping("/appointment/{appointmentId}")
    public VisitResponse getVisitByAppointment(@PathVariable Long appointmentId) {
        return visitService.findVisitByAppointment(appointmentId);
    }

    @GetMapping("/status/{status}")
    public List<VisitResponse> getVisitsByStatus(@PathVariable VisitStatus status) {
        return visitService.findVisitsByStatus(status);
    }

    @GetMapping("/date-range")
    public List<VisitResponse> getVisitsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        return visitService.findVisitsByDateRange(start, end);
    }
}
