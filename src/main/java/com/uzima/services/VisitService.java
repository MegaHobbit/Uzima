package com.uzima.services;

import com.uzima.Mapper.VisitMapper;
import com.uzima.dtos.VisitRequest;
import com.uzima.dtos.VisitResponse;
import com.uzima.enums.AppointmentStatus;
import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import com.uzima.models.Appointment;
import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.models.Visit;
import com.uzima.repository.AppointmentRepository;
import com.uzima.repository.DoctorRepository;
import com.uzima.repository.PatientRepository;
import com.uzima.repository.ServicePointRepository;
import com.uzima.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ServicePointRepository servicePointRepository;
    private final AppointmentRepository appointmentRepository;
    private final BusinessNumberService businessNumberService;

    @Transactional
    public VisitResponse createVisit(VisitRequest request) {
        Appointment appointment = resolveAppointmentForCreate(request.getAppointmentId());
        Patient patient = appointment == null ? getActivePatient(requireId(request.getPatientId(), "patientId"))
                : appointment.getPatient();
        Doctor doctor = appointment == null ? getActiveDoctor(requireId(request.getDoctorId(), "doctorId"))
                : appointment.getDoctor();
        ServicePoint servicePoint = appointment == null
                ? getActiveServicePoint(requireId(request.getServicePointId(), "servicePointId"))
                : appointment.getServicePoint();

        if (appointment != null) {
            validateOptionalMatch(request.getPatientId(), patient.getId(), "patient");
            validateOptionalMatch(request.getDoctorId(), doctor.getId(), "doctor");
            validateOptionalMatch(request.getServicePointId(), servicePoint.getId(), "service point");
        }

        validateDoctorServicePoint(doctor, servicePoint);
        VisitStatus initialStatus = request.getVisitStatus() == null ? VisitStatus.CHECKED_IN : request.getVisitStatus();
        if (initialStatus != VisitStatus.CHECKED_IN) {
            throw new IllegalArgumentException("New visits must start as CHECKED_IN.");
        }

        Visit visit = VisitMapper.fromRequest(request);
        visit.setVisitNumber(businessNumberService.nextVisitNumber());
        visit.setVisitDateTime(request.getVisitDateTime() == null ? LocalDateTime.now() : request.getVisitDateTime());
        visit.setVisitType(request.getVisitType() == null ? VisitType.OUTPATIENT : request.getVisitType());
        visit.setVisitStatus(initialStatus);
        visit.setDeletedFlag(false);
        visit.setAppointment(appointment);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setServicePoint(servicePoint);

        return VisitMapper.toResponse(visitRepository.save(visit));
    }

    @Transactional(readOnly = true)
    public VisitResponse getVisit(Long id) {
        return VisitMapper.toResponse(getActiveVisit(id));
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> getAllVisits() {
        return visitRepository.findAllByDeletedFlagFalse().stream()
                .map(VisitMapper::toResponse)
                .toList();
    }

    @Transactional
    public VisitResponse updateVisit(Long id, VisitRequest request) {
        Visit visit = getActiveVisit(id);

        VisitStatus requestedStatus = request.getVisitStatus();
        if (requestedStatus != null) {
            validateVisitStatusTransition(visit.getVisitStatus(), requestedStatus);
            visit.setVisitStatus(requestedStatus);
        }

        if (request.getVisitDateTime() != null) {
            visit.setVisitDateTime(request.getVisitDateTime());
        }
        if (request.getVisitType() != null) {
            visit.setVisitType(request.getVisitType());
        }
        visit.setChiefComplaint(request.getChiefComplaint());
        visit.setNotes(request.getNotes());

        updateRelationshipsIfRequested(visit, request);

        return VisitMapper.toResponse(visitRepository.save(visit));
    }

    @Transactional
    public VisitResponse deleteVisit(Long id) {
        Visit visit = getActiveVisit(id);
        visit.setDeletedFlag(true);
        return VisitMapper.toResponse(visitRepository.save(visit));
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> findVisitsByPatient(Long patientId) {
        return visitRepository.findByPatientIdAndDeletedFlagFalse(patientId).stream()
                .map(VisitMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> findVisitsByDoctor(Long doctorId) {
        return visitRepository.findByDoctorIdAndDeletedFlagFalse(doctorId).stream()
                .map(VisitMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public VisitResponse findVisitByAppointment(Long appointmentId) {
        Visit visit = visitRepository.findByAppointmentIdAndDeletedFlagFalse(appointmentId)
                .orElseThrow(() -> new RuntimeException(
                        "Visit for appointment with id: " + appointmentId + " could not be found"));
        return VisitMapper.toResponse(visit);
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> findVisitsByStatus(VisitStatus status) {
        return visitRepository.findByVisitStatusAndDeletedFlagFalse(status).stream()
                .map(VisitMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VisitResponse> findVisitsByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Both start and end date-times are required.");
        }
        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date-time cannot be before start date-time.");
        }
        return visitRepository.findByVisitDateTimeBetweenAndDeletedFlagFalse(start, end).stream()
                .map(VisitMapper::toResponse)
                .toList();
    }

    Visit getActiveVisit(Long id) {
        return visitRepository.findByIdAndDeletedFlagFalse(id)
                .orElseThrow(() -> new RuntimeException("Visit with id: " + id + " could not be found"));
    }

    private Appointment resolveAppointmentForCreate(Long appointmentId) {
        if (appointmentId == null) {
            return null;
        }

        Appointment appointment = getActiveAppointment(appointmentId);
        if (appointment.getStatus() == AppointmentStatus.CANCELLED || appointment.getStatus() == AppointmentStatus.NO_SHOW) {
            throw new IllegalArgumentException("Cannot create a visit from a cancelled or no-show appointment.");
        }

        visitRepository.findByAppointmentIdAndDeletedFlagFalse(appointmentId)
                .ifPresent(existingVisit -> {
                    throw new IllegalArgumentException(
                            "Appointment with id: " + appointmentId + " already has an active visit.");
                });

        return appointment;
    }

    private void updateRelationshipsIfRequested(Visit visit, VisitRequest request) {
        Appointment appointment = visit.getAppointment();
        if (request.getAppointmentId() != null && !Objects.equals(
                appointment == null ? null : appointment.getId(), request.getAppointmentId())) {
            appointment = resolveAppointmentForCreate(request.getAppointmentId());
            visit.setAppointment(appointment);
            visit.setPatient(appointment.getPatient());
            visit.setDoctor(appointment.getDoctor());
            visit.setServicePoint(appointment.getServicePoint());
            return;
        }

        if (appointment != null) {
            validateOptionalMatch(request.getPatientId(), appointment.getPatient().getId(), "patient");
            validateOptionalMatch(request.getDoctorId(), appointment.getDoctor().getId(), "doctor");
            validateOptionalMatch(request.getServicePointId(), appointment.getServicePoint().getId(), "service point");
            return;
        }

        if (request.getPatientId() != null) {
            visit.setPatient(getActivePatient(request.getPatientId()));
        }
        if (request.getDoctorId() != null) {
            visit.setDoctor(getActiveDoctor(request.getDoctorId()));
        }
        if (request.getServicePointId() != null) {
            visit.setServicePoint(getActiveServicePoint(request.getServicePointId()));
        }
        validateDoctorServicePoint(visit.getDoctor(), visit.getServicePoint());
    }

    private Patient getActivePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient with id: " + id + " could not be found"));
        if (Boolean.TRUE.equals(patient.getIsDeleted())) {
            throw new RuntimeException("Patient with id: " + id + " has been deleted");
        }
        return patient;
    }

    private Doctor getActiveDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));
        if (Boolean.TRUE.equals(doctor.getDeletedFlag())) {
            throw new RuntimeException("Doctor with id: " + id + " has been deleted");
        }
        return doctor;
    }

    private ServicePoint getActiveServicePoint(Long id) {
        ServicePoint servicePoint = servicePointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Service Point with id: " + id + " could not be found"));
        if (Boolean.TRUE.equals(servicePoint.getDeletedFlag())) {
            throw new RuntimeException("Service Point with id: " + id + " has been deleted");
        }
        return servicePoint;
    }

    private Appointment getActiveAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment with id: " + id + " could not be found"));
        if (appointment.isDeletedFlag()) {
            throw new RuntimeException("Appointment with id: " + id + " has been deleted");
        }
        return appointment;
    }

    private void validateOptionalMatch(Long requestedId, Long actualId, String relationshipName) {
        if (requestedId != null && !requestedId.equals(actualId)) {
            throw new IllegalArgumentException("Requested " + relationshipName
                    + " does not match the selected appointment.");
        }
    }

    private void validateDoctorServicePoint(Doctor doctor, ServicePoint servicePoint) {
        if (doctor != null && doctor.getServicePoint() != null && servicePoint != null
                && !doctor.getServicePoint().getId().equals(servicePoint.getId())) {
            throw new IllegalArgumentException("Doctor does not belong to the selected service point.");
        }
    }

    private Long requireId(Long id, String fieldName) {
        if (id == null) {
            throw new IllegalArgumentException(fieldName + " is required when no appointmentId is supplied.");
        }
        return id;
    }

    private void validateVisitStatusTransition(VisitStatus currentStatus, VisitStatus requestedStatus) {
        if (currentStatus == requestedStatus) {
            return;
        }

        boolean allowed = switch (currentStatus) {
            case CHECKED_IN -> requestedStatus == VisitStatus.TRIAGED
                    || requestedStatus == VisitStatus.IN_CONSULTATION
                    || requestedStatus == VisitStatus.CANCELLED;
            case TRIAGED -> requestedStatus == VisitStatus.IN_CONSULTATION
                    || requestedStatus == VisitStatus.CANCELLED;
            case IN_CONSULTATION -> requestedStatus == VisitStatus.COMPLETED
                    || requestedStatus == VisitStatus.CANCELLED;
            case COMPLETED, CANCELLED -> false;
        };

        if (!allowed) {
            throw new IllegalArgumentException(
                    "Cannot move visit status from " + currentStatus + " to " + requestedStatus + ".");
        }
    }
}
