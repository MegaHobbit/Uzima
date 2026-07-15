package com.uzima.controllers;

import com.uzima.dtos.PatientData;
import com.uzima.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<List<PatientData>> savePatient(
            @RequestBody List<PatientData> patientData) {

        return patientService.createPatient(patientData);
    }

    @GetMapping("/Retrieve/{id}")
    public PatientData getPatient(@PathVariable("id") Long id)
    {
        return patientService.getPatient(id);
    }


    @PutMapping("/{id}")
    public PatientData updatePatient(
            @PathVariable("id") Long id,
            @RequestBody PatientData patientData) {

        return patientService.updatePatient(id, patientData);

    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable("id") Long id) {

        return patientService.deletePatient(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<PatientData>> getAllPatients() {

        return patientService.getAllPatients();
    }

}
