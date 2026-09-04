package com.uzima.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BusinessNumberService {

    @PersistenceContext
    private EntityManager entityManager;

    public String nextVisitNumber() {
        return nextNumber("VIS", "visit_number_seq");
    }

    public String nextPrescriptionNumber() {
        return nextNumber("RX", "prescription_number_seq");
    }

    public String nextBillNumber() {
        return nextNumber("BIL", "bill_number_seq");
    }

    public String nextReportNumber() {
        return nextNumber("RPT", "report_number_seq");
    }

    private String nextNumber(String prefix, String sequenceName) {
        Number sequenceValue = (Number) entityManager
                .createNativeQuery("SELECT nextval('" + sequenceName + "')")
                .getSingleResult();
        return "%s-%d-%06d".formatted(prefix, LocalDate.now().getYear(), sequenceValue.longValue());
    }
}
