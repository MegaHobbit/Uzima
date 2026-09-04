package com.uzima.services;

import com.uzima.Mapper.BillingItemMapper;
import com.uzima.Mapper.BillingMapper;
import com.uzima.dtos.BillingItemRequest;
import com.uzima.dtos.BillingRequest;
import com.uzima.dtos.BillingResponse;
import com.uzima.enums.BillingStatus;
import com.uzima.models.Billing;
import com.uzima.models.BillingItem;
import com.uzima.models.Visit;
import com.uzima.repository.BillingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;
    private final VisitService visitService;
    private final BusinessNumberService businessNumberService;

    @Transactional
    public BillingResponse createBilling(BillingRequest request) {
        Visit visit = visitService.getActiveVisit(request.getVisitId());

        Billing billing = BillingMapper.fromRequest(request);
        billing.setBillNumber(businessNumberService.nextBillNumber());
        billing.setBillingDate(LocalDateTime.now());
        BillingStatus status = request.getStatus() == null ? BillingStatus.DRAFT : request.getStatus();
        if (status == BillingStatus.PARTIALLY_PAID || status == BillingStatus.PAID || status == BillingStatus.VOIDED) {
            throw new IllegalArgumentException("A new bill must start as DRAFT or ISSUED.");
        }
        billing.setStatus(status);
        billing.setDeletedFlag(false);
        billing.setVisit(visit);
        billing.setPatient(visit.getPatient());
        replaceItemsAndTotals(billing, request.getItems());

        return BillingMapper.toResponse(billingRepository.save(billing));
    }

    @Transactional(readOnly = true)
    public BillingResponse getBilling(Long id) {
        return BillingMapper.toResponse(getActiveBilling(id));
    }

    @Transactional(readOnly = true)
    public List<BillingResponse> getAllBillings() {
        return billingRepository.findAllByDeletedFlagFalse().stream()
                .map(BillingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BillingResponse> getBillingsByVisit(Long visitId) {
        return billingRepository.findByVisitIdAndDeletedFlagFalse(visitId).stream()
                .map(BillingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BillingResponse> getBillingsByPatient(Long patientId) {
        return billingRepository.findByPatientIdAndDeletedFlagFalse(patientId).stream()
                .map(BillingMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<BillingResponse> getBillingsByStatus(BillingStatus status) {
        return billingRepository.findByStatusAndDeletedFlagFalse(status).stream()
                .map(BillingMapper::toResponse)
                .toList();
    }

    @Transactional
    public BillingResponse updateBilling(Long id, BillingRequest request) {
        Billing billing = getActiveBilling(id);
        if (billing.getStatus() == BillingStatus.PAID) {
            throw new IllegalArgumentException("Cannot modify a paid bill.");
        }
        if (billing.getStatus() == BillingStatus.VOIDED) {
            throw new IllegalArgumentException("Cannot modify a voided bill.");
        }
        if (!billing.getVisit().getId().equals(request.getVisitId())) {
            throw new IllegalArgumentException("A bill cannot be moved to a different visit.");
        }

        BillingStatus requestedStatus = request.getStatus() == null ? billing.getStatus() : request.getStatus();
        validateBillingStatusTransition(billing.getStatus(), requestedStatus);
        billing.setStatus(requestedStatus);
        billing.setNotes(request.getNotes());
        replaceItemsAndTotals(billing, request.getItems());

        return BillingMapper.toResponse(billingRepository.save(billing));
    }

    @Transactional
    public BillingResponse deleteBilling(Long id) {
        Billing billing = getActiveBilling(id);
        billing.setDeletedFlag(true);
        return BillingMapper.toResponse(billingRepository.save(billing));
    }

    private Billing getActiveBilling(Long id) {
        return billingRepository.findByIdAndDeletedFlagFalse(id)
                .orElseThrow(() -> new RuntimeException("Billing with id: " + id + " could not be found"));
    }

    private void replaceItemsAndTotals(Billing billing, List<BillingItemRequest> itemRequests) {
        if (itemRequests == null || itemRequests.isEmpty()) {
            throw new IllegalArgumentException("Billing must contain at least one item.");
        }

        billing.getItems().clear();
        BigDecimal total = BigDecimal.ZERO;
        for (BillingItemRequest itemRequest : itemRequests) {
            validateBillingItem(itemRequest);
            BillingItem item = BillingItemMapper.fromRequest(itemRequest);
            BigDecimal amount = itemRequest.getQuantity().multiply(itemRequest.getUnitPrice());
            item.setAmount(amount);
            item.setBilling(billing);
            item.setDeletedFlag(false);
            billing.getItems().add(item);
            total = total.add(amount);
        }
        billing.setTotalAmount(total);
    }

    private void validateBillingItem(BillingItemRequest itemRequest) {
        if (itemRequest.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Billing item quantity must be greater than zero.");
        }
        if (itemRequest.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Billing item unit price cannot be negative.");
        }
    }

    private void validateBillingStatusTransition(BillingStatus currentStatus, BillingStatus requestedStatus) {
        if (currentStatus == requestedStatus) {
            return;
        }

        boolean allowed = switch (currentStatus) {
            case DRAFT -> requestedStatus == BillingStatus.ISSUED
                    || requestedStatus == BillingStatus.VOIDED;
            case ISSUED -> requestedStatus == BillingStatus.PARTIALLY_PAID
                    || requestedStatus == BillingStatus.PAID
                    || requestedStatus == BillingStatus.VOIDED;
            case PARTIALLY_PAID -> requestedStatus == BillingStatus.PAID
                    || requestedStatus == BillingStatus.VOIDED;
            case PAID, VOIDED -> false;
        };

        if (!allowed) {
            throw new IllegalArgumentException(
                    "Cannot move billing status from " + currentStatus + " to " + requestedStatus + ".");
        }
    }
}
