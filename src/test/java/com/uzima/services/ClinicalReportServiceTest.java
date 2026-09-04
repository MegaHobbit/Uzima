package com.uzima.services;

import com.uzima.dtos.ClinicalReportRequest;
import com.uzima.dtos.ClinicalReportResponse;
import com.uzima.enums.ClinicalReportType;
import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import com.uzima.models.ClinicalReport;
import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.models.Visit;
import com.uzima.repository.ClinicalReportRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClinicalReportServiceTest {

    @Mock
    private ClinicalReportRepository clinicalReportRepository;

    @Mock
    private VisitService visitService;

    @Mock
    private BusinessNumberService businessNumberService;

    @InjectMocks
    private ClinicalReportService clinicalReportService;

    @Test
    void createClinicalReportUsesVisitPatientAndDoctorAndDefaultsReportType() {
        ClinicalReportRequest request = new ClinicalReportRequest();
        request.setVisitId(5L);
        request.setTitle("Outpatient summary");
        request.setFindings("Stable patient.");

        when(visitService.getActiveVisit(5L)).thenReturn(visit());
        when(businessNumberService.nextReportNumber()).thenReturn("RPT-2026-000001");
        when(clinicalReportRepository.save(any(ClinicalReport.class))).thenAnswer(invocation -> {
            ClinicalReport report = invocation.getArgument(0);
            report.setId(11L);
            return report;
        });

        ClinicalReportResponse response = clinicalReportService.createClinicalReport(request);

        assertThat(response.getReportNumber()).isEqualTo("RPT-2026-000001");
        assertThat(response.getReportType()).isEqualTo(ClinicalReportType.GENERAL);
        assertThat(response.getPatientId()).isEqualTo(1L);
        assertThat(response.getDoctorId()).isEqualTo(2L);
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
        visit.setVisitStatus(VisitStatus.COMPLETED);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setServicePoint(servicePoint);
        visit.setDeletedFlag(false);
        return visit;
    }
}
