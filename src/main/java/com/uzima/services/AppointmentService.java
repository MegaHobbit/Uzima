package com.uzima.services;

import com.uzima.Mapper.AppointmentMapper;
import com.uzima.dtos.AppointmentRequest;
import com.uzima.dtos.AppointmentResponse;
import com.uzima.models.Appointment;
import com.uzima.repository.AppointmentRepository;
import com.uzima.repository.DoctorRepository;
import com.uzima.repository.PatientRepository;
import com.uzima.repository.ServicePointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ServicePointRepository servicePointRepository;
    private final PatientRepository patientRepository;


    public List<AppointmentResponse> createNewAppointments(List<AppointmentRequest> requests) {
        return requests.stream()
                .map(request -> {
                    // 1. Map request to entity
                    Appointment appointment = AppointmentMapper.fromRequest(request);
                    appointment.setDeletedFlag(false);

                    // 2. Fetch and set relations
                    appointment.setDoctor(
                            doctorRepository.findById(request.getDoctorId())
                                    .orElseThrow(() -> new RuntimeException(
                                            "Doctor with id: " + request.getDoctorId() + " could not be found"))
                    );

                    appointment.setPatient(
                            patientRepository.findById(request.getPatientId())
                                    .orElseThrow(() -> new RuntimeException(
                                            "Patient with id: " + request.getPatientId() + " could not be found"))
                    );

                    appointment.setServicePoint(
                            servicePointRepository.findById(request.getServicePointId())
                                    .orElseThrow(() -> new RuntimeException(
                                            "Service Point with id: " + request.getServicePointId() + " could not be found"))
                    );

                    // 3. Save to database
                    Appointment savedAppointment = appointmentRepository.save(appointment);

                    // 4. Map saved entity to response
                    return AppointmentMapper.toResponse(savedAppointment);
                })
                .toList();
    }


    public AppointmentResponse getAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment with id: " + id + " could not be found"));

        return AppointmentMapper.toResponse(appointment);
    }

    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {

        if (appointmentRepository.findAllByDeletedFlag(false).isEmpty()) {
            throw new RuntimeException("No appointments found.");
        }

        List<Appointment> appointments = appointmentRepository.findAll();

        List<AppointmentResponse> appointmentsList = appointments.stream()
                .map(AppointmentMapper::toResponse)
                .toList();

        return ResponseEntity.ok(appointmentsList);
    }

    public AppointmentResponse updateThisAppointment(Long id, AppointmentRequest request) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment with id: " + id + " could not be found"));


        AppointmentMapper.updateAppointment(appointment, request);


        appointment.setDoctor(doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + request.getDoctorId() + " could not be found")));

        appointment.setServicePoint(servicePointRepository.findById(request.getServicePointId())
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + request.getServicePointId() + " could not be found")));

        appointment.setPatient(patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient with id: " + request.getPatientId() + " could not be found")));

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toResponse(savedAppointment);

    }

    public AppointmentResponse deleteThisAppointment(Long id) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment with id: " + id + " could not be found"));

        appointment.setDeletedFlag(true);
        Appointment deletedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment with id: {} is deleted!!!", id);

        return AppointmentMapper.toResponse(deletedAppointment);
    }


}
