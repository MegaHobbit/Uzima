package com.uzima.Mapper;

import com.uzima.dtos.MedicationRequest;
import com.uzima.dtos.MedicationResponse;
import com.uzima.models.Medication;

public class MedicationMapper {

    public static Medication fromRequest(MedicationRequest request) {
        Medication medication = new Medication();
        updateMedication(medication, request);
        return medication;
    }

    public static void updateMedication(Medication medication, MedicationRequest request) {
        medication.setMedicationName(request.getMedicationName());
        medication.setGenericName(request.getGenericName());
        medication.setDosageForm(request.getDosageForm());
        medication.setStrength(request.getStrength());
        medication.setDescription(request.getDescription());
        if (request.getActiveFlag() != null) {
            medication.setActiveFlag(request.getActiveFlag());
        }
    }

    public static MedicationResponse toResponse(Medication medication) {
        MedicationResponse response = new MedicationResponse();
        response.setId(medication.getId());
        response.setMedicationName(medication.getMedicationName());
        response.setGenericName(medication.getGenericName());
        response.setDosageForm(medication.getDosageForm());
        response.setStrength(medication.getStrength());
        response.setDescription(medication.getDescription());
        response.setActiveFlag(medication.getActiveFlag());
        response.setDeletedFlag(medication.getDeletedFlag());
        return response;
    }
}
