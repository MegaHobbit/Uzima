package com.uzima.services;

import com.uzima.dtos.ConsultationRequest;
import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.models.Visit;
import com.uzima.repository.ConsultationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultationServiceTest {

    @Mock
    private ConsultationRepository consultationRepository;

    @Mock
    private VisitService visitService;

    @InjectMocks
    private ConsultationService consultationService;

    @Test
    void createConsultationRejectsSecondActiveConsultationForVisit() {
        Visit visit = visit();
        ConsultationRequest request = new ConsultationRequest();
        request.setVisitId(5L);

        when(visitService.getActiveVisit(5L)).thenReturn(visit);
        when(consultationRepository.existsByVisitIdAndDeletedFlagFalse(5L)).thenReturn(true);

        assertThatThrownBy(() -> consultationService.createConsultation(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("already has an active consultation");
    }

    private Visit visit() {
        ServicePoint servicePoint = new ServicePoint();
        servicePoint.setId(3L);

        Doctor doctor = new Doctor();
        doctor.setId(2L);
        doctor.setServicePoint(servicePoint);

        Patient patient = new Patient();
        patient.setId(1L);
        patient.setDoctor(doctor);

        Visit visit = new Visit();
        visit.setId(5L);
        visit.setVisitNumber("VIS-2026-000001");
        visit.setVisitType(VisitType.OUTPATIENT);
        visit.setVisitStatus(VisitStatus.CHECKED_IN);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setServicePoint(servicePoint);
        visit.setDeletedFlag(false);
        return visit;
    }
}
