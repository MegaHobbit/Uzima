package com.uzima.Mapper;

import com.uzima.dtos.ConsultationRequest;
import com.uzima.dtos.ConsultationResponse;
import com.uzima.models.Consultation;
import com.uzima.models.Doctor;
import com.uzima.models.Visit;

public class ConsultationMapper {

    public static Consultation fromRequest(ConsultationRequest request) {
        Consultation consultation = new Consultation();
        updateConsultation(consultation, request);
        return consultation;
    }

    public static void updateConsultation(Consultation consultation, ConsultationRequest request) {
        consultation.setConsultationDateTime(request.getConsultationDateTime());
        consultation.setChiefComplaint(request.getChiefComplaint());
        consultation.setHistoryOfPresentIllness(request.getHistoryOfPresentIllness());
        consultation.setExaminationFindings(request.getExaminationFindings());
        consultation.setAssessment(request.getAssessment());
        consultation.setDiagnosis(request.getDiagnosis());
        consultation.setTreatmentPlan(request.getTreatmentPlan());
        consultation.setNotes(request.getNotes());
    }

    public static ConsultationResponse toResponse(Consultation consultation) {
        ConsultationResponse response = new ConsultationResponse();
        response.setId(consultation.getId());
        response.setConsultationDateTime(consultation.getConsultationDateTime());
        response.setChiefComplaint(consultation.getChiefComplaint());
        response.setHistoryOfPresentIllness(consultation.getHistoryOfPresentIllness());
        response.setExaminationFindings(consultation.getExaminationFindings());
        response.setAssessment(consultation.getAssessment());
        response.setDiagnosis(consultation.getDiagnosis());
        response.setTreatmentPlan(consultation.getTreatmentPlan());
        response.setNotes(consultation.getNotes());
        response.setDeletedFlag(consultation.getDeletedFlag());

        Visit visit = consultation.getVisit();
        if (visit != null) {
            response.setVisitId(visit.getId());
            response.setVisitNumber(visit.getVisitNumber());
            if (visit.getPatient() != null) {
                response.setPatientId(visit.getPatient().getId());
            }
        }

        Doctor doctor = consultation.getDoctor();
        if (doctor != null) {
            response.setDoctorId(doctor.getId());
            response.setDoctorName(fullName(doctor.getFirstName(), doctor.getLastName()));
        }

        return response;
    }

    private static String fullName(String firstName, String lastName) {
        String first = firstName == null ? "" : firstName;
        String last = lastName == null ? "" : lastName;
        return (first + " " + last).trim();
    }
}
