package com.uzima.Mapper;

import com.uzima.dtos.VisitRequest;
import com.uzima.dtos.VisitResponse;
import com.uzima.models.Appointment;
import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.models.Visit;

public class VisitMapper {

    public static Visit fromRequest(VisitRequest request) {
        Visit visit = new Visit();
        updateVisit(visit, request);
        return visit;
    }

    public static void updateVisit(Visit visit, VisitRequest request) {
        visit.setVisitDateTime(request.getVisitDateTime());
        visit.setVisitType(request.getVisitType());
        visit.setVisitStatus(request.getVisitStatus());
        visit.setChiefComplaint(request.getChiefComplaint());
        visit.setNotes(request.getNotes());
    }

    public static VisitResponse toResponse(Visit visit) {
        VisitResponse response = new VisitResponse();
        response.setId(visit.getId());
        response.setVisitNumber(visit.getVisitNumber());
        response.setVisitDateTime(visit.getVisitDateTime());
        response.setVisitType(visit.getVisitType());
        response.setVisitStatus(visit.getVisitStatus());
        response.setChiefComplaint(visit.getChiefComplaint());
        response.setNotes(visit.getNotes());
        response.setDeletedFlag(visit.getDeletedFlag());

        Patient patient = visit.getPatient();
        if (patient != null) {
            response.setPatientId(patient.getId());
            response.setPatientNumber(patient.getPatientNumber());
            response.setPatientName(fullName(patient.getFirstName(), patient.getLastName()));
        }

        Doctor doctor = visit.getDoctor();
        if (doctor != null) {
            response.setDoctorId(doctor.getId());
            response.setDoctorNumber(doctor.getDoctorNumber());
            response.setDoctorName(fullName(doctor.getFirstName(), doctor.getLastName()));
        }

        ServicePoint servicePoint = visit.getServicePoint();
        if (servicePoint != null) {
            response.setServicePointId(servicePoint.getId());
            response.setServicePointName(servicePoint.getPointName());
        }

        Appointment appointment = visit.getAppointment();
        if (appointment != null) {
            response.setAppointmentId(appointment.getId());
        }

        return response;
    }

    private static String fullName(String firstName, String lastName) {
        String first = firstName == null ? "" : firstName;
        String last = lastName == null ? "" : lastName;
        return (first + " " + last).trim();
    }
}
