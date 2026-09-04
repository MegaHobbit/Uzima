package com.uzima.services;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private ServicePointRepository servicePointRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private BusinessNumberService businessNumberService;

    @InjectMocks
    private VisitService visitService;

    @Test
    void createVisitFromAppointmentUsesAppointmentRelationshipsAndBusinessNumber() {
        ServicePoint servicePoint = servicePoint();
        Doctor doctor = doctor(servicePoint);
        Patient patient = patient(doctor);
        Appointment appointment = appointment(patient, doctor, servicePoint);

        VisitRequest request = new VisitRequest();
        request.setAppointmentId(4L);
        request.setVisitType(VisitType.OUTPATIENT);
        request.setChiefComplaint("Fever");

        when(appointmentRepository.findById(4L)).thenReturn(Optional.of(appointment));
        when(visitRepository.findByAppointmentIdAndDeletedFlagFalse(4L)).thenReturn(Optional.empty());
        when(businessNumberService.nextVisitNumber()).thenReturn("VIS-2026-000001");
        when(visitRepository.save(any(Visit.class))).thenAnswer(invocation -> {
            Visit visit = invocation.getArgument(0);
            visit.setId(5L);
            return visit;
        });

        VisitResponse response = visitService.createVisit(request);

        assertThat(response.getId()).isEqualTo(5L);
        assertThat(response.getVisitNumber()).isEqualTo("VIS-2026-000001");
        assertThat(response.getVisitStatus()).isEqualTo(VisitStatus.CHECKED_IN);
        assertThat(response.getPatientId()).isEqualTo(1L);
        assertThat(response.getDoctorId()).isEqualTo(2L);
        assertThat(response.getServicePointId()).isEqualTo(3L);
        assertThat(response.getAppointmentId()).isEqualTo(4L);
    }

    @Test
    void updateVisitRejectsInvalidStatusTransitionFromCompleted() {
        Visit visit = visit(patient(doctor(servicePoint())), doctor(servicePoint()), servicePoint());
        visit.setVisitStatus(VisitStatus.COMPLETED);

        VisitRequest request = new VisitRequest();
        request.setVisitStatus(VisitStatus.TRIAGED);

        when(visitRepository.findByIdAndDeletedFlagFalse(5L)).thenReturn(Optional.of(visit));

        assertThatThrownBy(() -> visitService.updateVisit(5L, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Cannot move visit status from COMPLETED to TRIAGED");
    }

    private ServicePoint servicePoint() {
        ServicePoint servicePoint = new ServicePoint();
        servicePoint.setId(3L);
        servicePoint.setPointName("Consultation");
        servicePoint.setDeletedFlag(false);
        return servicePoint;
    }

    private Doctor doctor(ServicePoint servicePoint) {
        Doctor doctor = new Doctor();
        doctor.setId(2L);
        doctor.setFirstName("Amina");
        doctor.setLastName("Otieno");
        doctor.setDoctorNumber("DOC-001");
        doctor.setDeletedFlag(false);
        doctor.setServicePoint(servicePoint);
        return doctor;
    }

    private Patient patient(Doctor doctor) {
        Patient patient = new Patient();
        patient.setId(1L);
        patient.setFirstName("John");
        patient.setLastName("Kamau");
        patient.setPatientNumber("PAT-001");
        patient.setIsDeleted(false);
        patient.setDoctor(doctor);
        return patient;
    }

    private Appointment appointment(Patient patient, Doctor doctor, ServicePoint servicePoint) {
        Appointment appointment = new Appointment();
        appointment.setId(4L);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment.setDeletedFlag(false);
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setServicePoint(servicePoint);
        return appointment;
    }

    private Visit visit(Patient patient, Doctor doctor, ServicePoint servicePoint) {
        Visit visit = new Visit();
        visit.setId(5L);
        visit.setVisitNumber("VIS-2026-000001");
        visit.setVisitType(VisitType.OUTPATIENT);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setServicePoint(servicePoint);
        visit.setDeletedFlag(false);
        return visit;
    }
}
