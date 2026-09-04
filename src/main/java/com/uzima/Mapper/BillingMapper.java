package com.uzima.Mapper;

import com.uzima.dtos.BillingRequest;
import com.uzima.dtos.BillingResponse;
import com.uzima.models.Billing;
import com.uzima.models.Visit;

public class BillingMapper {

    public static Billing fromRequest(BillingRequest request) {
        Billing billing = new Billing();
        updateBilling(billing, request);
        return billing;
    }

    public static void updateBilling(Billing billing, BillingRequest request) {
        billing.setStatus(request.getStatus());
        billing.setNotes(request.getNotes());
    }

    public static BillingResponse toResponse(Billing billing) {
        BillingResponse response = new BillingResponse();
        response.setId(billing.getId());
        response.setBillNumber(billing.getBillNumber());
        response.setBillingDate(billing.getBillingDate());
        response.setStatus(billing.getStatus());
        response.setTotalAmount(billing.getTotalAmount());
        response.setNotes(billing.getNotes());
        response.setDeletedFlag(billing.getDeletedFlag());

        Visit visit = billing.getVisit();
        if (visit != null) {
            response.setVisitId(visit.getId());
            response.setVisitNumber(visit.getVisitNumber());
        }
        if (billing.getPatient() != null) {
            response.setPatientId(billing.getPatient().getId());
        }
        response.setItems(billing.getItems().stream()
                .map(BillingItemMapper::toResponse)
                .toList());

        return response;
    }
}
