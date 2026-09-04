package com.uzima.Mapper;

import com.uzima.dtos.ClinicalReportRequest;
import com.uzima.dtos.ClinicalReportResponse;
import com.uzima.models.ClinicalReport;
import com.uzima.models.Visit;

public class ClinicalReportMapper {

    public static ClinicalReport fromRequest(ClinicalReportRequest request) {
        ClinicalReport report = new ClinicalReport();
        updateClinicalReport(report, request);
        return report;
    }

    public static void updateClinicalReport(ClinicalReport report, ClinicalReportRequest request) {
        report.setReportType(request.getReportType());
        report.setReportDate(request.getReportDate());
        report.setTitle(request.getTitle());
        report.setFindings(request.getFindings());
        report.setConclusion(request.getConclusion());
        report.setRecommendations(request.getRecommendations());
        report.setNotes(request.getNotes());
    }

    public static ClinicalReportResponse toResponse(ClinicalReport report) {
        ClinicalReportResponse response = new ClinicalReportResponse();
        response.setId(report.getId());
        response.setReportNumber(report.getReportNumber());
        response.setReportType(report.getReportType());
        response.setReportDate(report.getReportDate());
        response.setTitle(report.getTitle());
        response.setFindings(report.getFindings());
        response.setConclusion(report.getConclusion());
        response.setRecommendations(report.getRecommendations());
        response.setNotes(report.getNotes());
        response.setDeletedFlag(report.getDeletedFlag());

        Visit visit = report.getVisit();
        if (visit != null) {
            response.setVisitId(visit.getId());
            response.setVisitNumber(visit.getVisitNumber());
        }
        if (report.getPatient() != null) {
            response.setPatientId(report.getPatient().getId());
        }
        if (report.getDoctor() != null) {
            response.setDoctorId(report.getDoctor().getId());
        }

        return response;
    }
}
