package com.Uzima.controllers;

import com.Uzima.dtos.DoctorData;
import com.Uzima.models.Doctor;
import com.Uzima.repository.DoctorRepository;
import com.Uzima.services.DoctorService;
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
    public DoctorData createDoctor(
            @RequestBody DoctorData doctorData) {

        return doctorService.postNewDoctor(doctorData);

    }

    @GetMapping("/get/doctor")
    public DoctorData getDoctor(@RequestParam("/id") Long id) {

        return doctorService.getDoctor(id);
    }

    @GetMapping("/get/all_doctors")
    public ResponseEntity<List<DoctorData>> getAllDoctor() {

        return doctorService.getAllDoctor();
    }

    @PutMapping("/update/doctor")
    public DoctorData updateDoctor(@RequestParam("/id") Long id,
                                   @RequestBody DoctorData doctorData) {

        return doctorService.updateThisDoctor(id, doctorData);
    }

    @DeleteMapping("/delete/doctor")
    public String deleteDoctor(@RequestParam("/id") Long id) {

        doctorService.deleteThisDoctor(id);

        return "Doctor with id: " + id + "is deleted!!";
    }
}
