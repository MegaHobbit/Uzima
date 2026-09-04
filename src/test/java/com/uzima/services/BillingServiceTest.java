package com.uzima.services;

import com.uzima.dtos.BillingItemRequest;
import com.uzima.dtos.BillingRequest;
import com.uzima.dtos.BillingResponse;
import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
import com.uzima.models.Billing;
import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.models.Visit;
import com.uzima.repository.BillingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BillingServiceTest {

    @Mock
    private BillingRepository billingRepository;

    @Mock
    private VisitService visitService;

    @Mock
    private BusinessNumberService businessNumberService;

    @InjectMocks
    private BillingService billingService;

    @Test
    void createBillingCalculatesLineAmountsAndTotal() {
        BillingRequest request = billingRequest();

        when(visitService.getActiveVisit(5L)).thenReturn(visit());
        when(businessNumberService.nextBillNumber()).thenReturn("BIL-2026-000001");
        when(billingRepository.save(any(Billing.class))).thenAnswer(invocation -> {
            Billing billing = invocation.getArgument(0);
            billing.setId(9L);
            billing.getItems().getFirst().setId(10L);
            return billing;
        });

        BillingResponse response = billingService.createBilling(request);

        assertThat(response.getBillNumber()).isEqualTo("BIL-2026-000001");
        assertThat(response.getTotalAmount()).isEqualByComparingTo("1000.00");
        assertThat(response.getItems()).hasSize(1);
        assertThat(response.getItems().getFirst().getAmount()).isEqualByComparingTo("1000.00");
    }

    private BillingRequest billingRequest() {
        BillingItemRequest item = new BillingItemRequest();
        item.setDescription("Consultation fee");
        item.setQuantity(new BigDecimal("2"));
        item.setUnitPrice(new BigDecimal("500.00"));

        BillingRequest request = new BillingRequest();
        request.setVisitId(5L);
        request.setItems(List.of(item));
        return request;
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
        visit.setVisitStatus(VisitStatus.IN_CONSULTATION);
        visit.setPatient(patient);
        visit.setDoctor(doctor);
        visit.setServicePoint(servicePoint);
        visit.setDeletedFlag(false);
        return visit;
    }
}
