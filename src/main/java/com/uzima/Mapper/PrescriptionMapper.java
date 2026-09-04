package com.uzima.Mapper;

import com.uzima.dtos.PrescriptionRequest;
import com.uzima.dtos.PrescriptionResponse;
import com.uzima.models.Prescription;
import com.uzima.models.Visit;

public class PrescriptionMapper {

    public static Prescription fromRequest(PrescriptionRequest request) {
        Prescription prescription = new Prescription();
        updatePrescription(prescription, request);
        return prescription;
    }

    public static void updatePrescription(Prescription prescription, PrescriptionRequest request) {
        prescription.setStatus(request.getStatus());
        prescription.setInstructions(request.getInstructions());
        prescription.setNotes(request.getNotes());
    }

    public static PrescriptionResponse toResponse(Prescription prescription) {
        PrescriptionResponse response = new PrescriptionResponse();
        response.setId(prescription.getId());
        response.setPrescriptionNumber(prescription.getPrescriptionNumber());
        response.setPrescriptionDate(prescription.getPrescriptionDate());
        response.setStatus(prescription.getStatus());
        response.setInstructions(prescription.getInstructions());
        response.setNotes(prescription.getNotes());
        response.setDeletedFlag(prescription.getDeletedFlag());

        Visit visit = prescription.getVisit();
        if (visit != null) {
            response.setVisitId(visit.getId());
            response.setVisitNumber(visit.getVisitNumber());
        }
        if (prescription.getPatient() != null) {
            response.setPatientId(prescription.getPatient().getId());
        }
        if (prescription.getDoctor() != null) {
            response.setDoctorId(prescription.getDoctor().getId());
        }
        response.setItems(prescription.getItems().stream()
                .map(PrescriptionItemMapper::toResponse)
                .toList());

        return response;
    }
}
