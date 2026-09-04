package com.uzima.Mapper;

import com.uzima.dtos.PrescriptionItemRequest;
import com.uzima.dtos.PrescriptionItemResponse;
import com.uzima.models.Medication;
import com.uzima.models.PrescriptionItem;

public class PrescriptionItemMapper {

    public static PrescriptionItem fromRequest(PrescriptionItemRequest request) {
        PrescriptionItem item = new PrescriptionItem();
        updatePrescriptionItem(item, request);
        return item;
    }

    public static void updatePrescriptionItem(PrescriptionItem item, PrescriptionItemRequest request) {
        item.setDosage(request.getDosage());
        item.setFrequency(request.getFrequency());
        item.setDuration(request.getDuration());
        item.setQuantity(request.getQuantity());
        item.setRoute(request.getRoute());
        item.setInstructions(request.getInstructions());
    }

    public static PrescriptionItemResponse toResponse(PrescriptionItem item) {
        PrescriptionItemResponse response = new PrescriptionItemResponse();
        response.setId(item.getId());
        response.setDosage(item.getDosage());
        response.setFrequency(item.getFrequency());
        response.setDuration(item.getDuration());
        response.setQuantity(item.getQuantity());
        response.setRoute(item.getRoute());
        response.setInstructions(item.getInstructions());

        Medication medication = item.getMedication();
        if (medication != null) {
            response.setMedicationId(medication.getId());
            response.setMedicationName(medication.getMedicationName());
        }

        return response;
    }
}
