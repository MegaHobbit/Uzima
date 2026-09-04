package com.uzima.services;

import com.uzima.Mapper.ClinicalReportMapper;
import com.uzima.dtos.ClinicalReportRequest;
import com.uzima.dtos.ClinicalReportResponse;
import com.uzima.enums.ClinicalReportType;
import com.uzima.enums.VisitStatus;
import com.uzima.models.ClinicalReport;
import com.uzima.models.Visit;
import com.uzima.repository.ClinicalReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicalReportService {

    private final ClinicalReportRepository clinicalReportRepository;
    private final VisitService visitService;
    private final BusinessNumberService businessNumberService;

    @Transactional
    public ClinicalReportResponse createClinicalReport(ClinicalReportRequest request) {
        Visit visit = visitService.getActiveVisit(request.getVisitId());
        if (visit.getVisitStatus() == VisitStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot create a clinical report for a cancelled visit.");
        }

        ClinicalReport report = ClinicalReportMapper.fromRequest(request);
        report.setReportNumber(businessNumberService.nextReportNumber());
        report.setReportType(request.getReportType() == null ? ClinicalReportType.GENERAL : request.getReportType());
        report.setReportDate(request.getReportDate() == null ? LocalDateTime.now() : request.getReportDate());
        report.setDeletedFlag(false);
        report.setVisit(visit);
        report.setPatient(visit.getPatient());
        report.setDoctor(visit.getDoctor());

        return ClinicalReportMapper.toResponse(clinicalReportRepository.save(report));
    }

    @Transactional(readOnly = true)
    public ClinicalReportResponse getClinicalReport(Long id) {
        return ClinicalReportMapper.toResponse(getActiveClinicalReport(id));
    }

    @Transactional(readOnly = true)
    public List<ClinicalReportResponse> getAllClinicalReports() {
        return clinicalReportRepository.findAllByDeletedFlagFalse().stream()
                .map(ClinicalReportMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClinicalReportResponse> getReportsByVisit(Long visitId) {
        return clinicalReportRepository.findByVisitIdAndDeletedFlagFalse(visitId).stream()
                .map(ClinicalReportMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClinicalReportResponse> getReportsByPatient(Long patientId) {
        return clinicalReportRepository.findByPatientIdAndDeletedFlagFalse(patientId).stream()
                .map(ClinicalReportMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClinicalReportResponse> getReportsByDoctor(Long doctorId) {
        return clinicalReportRepository.findByDoctorIdAndDeletedFlagFalse(doctorId).stream()
                .map(ClinicalReportMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClinicalReportResponse> getReportsByType(ClinicalReportType reportType) {
        return clinicalReportRepository.findByReportTypeAndDeletedFlagFalse(reportType).stream()
                .map(ClinicalReportMapper::toResponse)
                .toList();
    }

    @Transactional
    public ClinicalReportResponse updateClinicalReport(Long id, ClinicalReportRequest request) {
        ClinicalReport report = getActiveClinicalReport(id);
        LocalDateTime existingReportDate = report.getReportDate();
        if (!report.getVisit().getId().equals(request.getVisitId())) {
            throw new IllegalArgumentException("A clinical report cannot be moved to a different visit.");
        }
        if (report.getVisit().getVisitStatus() == VisitStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify a clinical report for a cancelled visit.");
        }

        ClinicalReportMapper.updateClinicalReport(report, request);
        report.setReportType(request.getReportType() == null ? ClinicalReportType.GENERAL : request.getReportType());
        report.setReportDate(request.getReportDate() == null ? existingReportDate : request.getReportDate());

        return ClinicalReportMapper.toResponse(clinicalReportRepository.save(report));
    }

    @Transactional
    public ClinicalReportResponse deleteClinicalReport(Long id) {
        ClinicalReport report = getActiveClinicalReport(id);
        report.setDeletedFlag(true);
        return ClinicalReportMapper.toResponse(clinicalReportRepository.save(report));
    }

    private ClinicalReport getActiveClinicalReport(Long id) {
        return clinicalReportRepository.findByIdAndDeletedFlagFalse(id)
                .orElseThrow(() -> new RuntimeException("Clinical report with id: " + id + " could not be found"));
    }
}
