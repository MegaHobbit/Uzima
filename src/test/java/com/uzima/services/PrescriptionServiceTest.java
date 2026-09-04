package com.uzima.services;

import com.uzima.dtos.PrescriptionItemRequest;
import com.uzima.dtos.PrescriptionRequest;
import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import com.uzima.models.Doctor;
import com.uzima.models.Medication;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.models.Visit;
import com.uzima.repository.MedicationRepository;
import com.uzima.repository.PrescriptionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private VisitService visitService;

    @Mock
    private BusinessNumberService businessNumberService;

    @InjectMocks
    private PrescriptionService prescriptionService;

    @Test
    void createPrescriptionRejectsInactiveMedication() {
        PrescriptionRequest request = prescriptionRequest();
        Medication medication = new Medication();
        medication.setId(8L);
        medication.setMedicationName("Paracetamol");
        medication.setActiveFlag(false);
        medication.setDeletedFlag(false);

        when(visitService.getActiveVisit(5L)).thenReturn(visit());
        when(businessNumberService.nextPrescriptionNumber()).thenReturn("RX-2026-000001");
        when(medicationRepository.findByIdAndDeletedFlagFalse(8L)).thenReturn(Optional.of(medication));

        assertThatThrownBy(() -> prescriptionService.createPrescription(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("is inactive");

        verify(prescriptionRepository, never()).save(any());
    }

    private PrescriptionRequest prescriptionRequest() {
        PrescriptionItemRequest item = new PrescriptionItemRequest();
        item.setMedicationId(8L);
        item.setDosage("500 mg");
        item.setFrequency("Three times daily");
        item.setDuration("5 days");
        item.setQuantity(15);

        PrescriptionRequest request = new PrescriptionRequest();
        request.setVisitId(5L);
        request.setItems(List.of(item));
        return request;
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
        visit.setVisitStatus(VisitStatus.IN_CONSULTATION);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setServicePoint(servicePoint);
        visit.setDeletedFlag(false);
        return visit;
    }
}
