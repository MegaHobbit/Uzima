package com.uzima.controllers;

import com.uzima.dtos.AppointmentRequest;
import com.uzima.dtos.AppointmentResponse;
import com.uzima.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<List<AppointmentResponse>> createAppointment(
            @RequestBody List<AppointmentRequest> requests) {

        return ResponseEntity.ok(appointmentService.createNewAppointments(requests));
    }

    @GetMapping("/Retrieve/{id}")
    public AppointmentResponse getAppointment(@PathVariable("id") Long id) {
        return appointmentService.getAppointment(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointment() {

        return appointmentService.getAllAppointments();
    }


    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponse> updateAppointment(
            @PathVariable("id") Long id,
            @RequestBody AppointmentRequest appointmentRequest) {

        return ResponseEntity.ok(appointmentService.updateThisAppointment(id, appointmentRequest));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AppointmentResponse> deleteAppointment(@PathVariable("id") Long id) {

        return ResponseEntity.ok(appointmentService.deleteThisAppointment(id));
    }
}
