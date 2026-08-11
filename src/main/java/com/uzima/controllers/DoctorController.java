package com.uzima.controllers;

import com.uzima.dtos.DoctorRequest;
import com.uzima.dtos.DoctorResponse;
import com.uzima.repository.DoctorRepository;
import com.uzima.services.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorRepository doctorRepository;


    @PostMapping("/create_doctor")
    public ResponseEntity<List<DoctorResponse>> createDoctor(@RequestBody List<DoctorRequest> doctorRequest) {

        return doctorService.postNewDoctors(doctorRequest);

    }

    @GetMapping("/get/doctor")
    public DoctorResponse getDoctor(@RequestParam("/id") Long id) {

        return doctorService.getDoctor(id);
    }

    @GetMapping("/get/all_doctors")
    public ResponseEntity<List<DoctorResponse>> getAllDoctor() {

        return doctorService.getAllDoctor();
    }

    @PutMapping("/update/doctor")
    public DoctorResponse updateDoctor(@RequestParam("/id") Long id, @RequestBody DoctorRequest request) {

        return doctorService.updateThisDoctor(id, request);
    }

    @DeleteMapping("/delete/doctor")
    public String deleteDoctor(@RequestParam("/id") Long id) {

        doctorService.deleteThisDoctor(id);

        return "Doctor with id: " + id + "is deleted!!";
    }
}
