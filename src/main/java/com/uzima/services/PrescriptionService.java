package com.uzima.services;

import com.uzima.Mapper.PrescriptionItemMapper;
import com.uzima.Mapper.PrescriptionMapper;
import com.uzima.dtos.PrescriptionItemRequest;
import com.uzima.dtos.PrescriptionRequest;
import com.uzima.dtos.PrescriptionResponse;
import com.uzima.enums.PrescriptionStatus;
import com.uzima.enums.VisitStatus;
import com.uzima.models.Medication;
import com.uzima.models.Prescription;
import com.uzima.models.PrescriptionItem;
import com.uzima.models.Visit;
import com.uzima.repository.MedicationRepository;
import com.uzima.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicationRepository medicationRepository;
    private final VisitService visitService;
    private final BusinessNumberService businessNumberService;

    @Transactional
    public PrescriptionResponse createPrescription(PrescriptionRequest request) {
        Visit visit = visitService.getActiveVisit(request.getVisitId());
        validateVisitCanReceivePrescription(visit);
        PrescriptionStatus status = request.getStatus() == null ? PrescriptionStatus.DRAFT : request.getStatus();
        if (status == PrescriptionStatus.DISPENSED) {
            throw new IllegalArgumentException("A new prescription cannot start as DISPENSED.");
        }
        if (status == PrescriptionStatus.CANCELLED) {
            throw new IllegalArgumentException("A new prescription cannot start as CANCELLED.");
        }

        Prescription prescription = PrescriptionMapper.fromRequest(request);
        prescription.setPrescriptionNumber(businessNumberService.nextPrescriptionNumber());
        prescription.setPrescriptionDate(LocalDateTime.now());
        prescription.setStatus(status);
        prescription.setDeletedFlag(false);
        prescription.setVisit(visit);
        prescription.setPatient(visit.getPatient());
        prescription.setDoctor(visit.getDoctor());
        replaceItems(prescription, request.getItems());

        return PrescriptionMapper.toResponse(prescriptionRepository.save(prescription));
    }

    @Transactional(readOnly = true)
    public PrescriptionResponse getPrescription(Long id) {
        return PrescriptionMapper.toResponse(getActivePrescription(id));
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAllByDeletedFlagFalse().stream()
                .map(PrescriptionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByVisit(Long visitId) {
        return prescriptionRepository.findByVisitIdAndDeletedFlagFalse(visitId).stream()
                .map(PrescriptionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByPatient(Long patientId) {
        return prescriptionRepository.findByPatientIdAndDeletedFlagFalse(patientId).stream()
                .map(PrescriptionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByDoctor(Long doctorId) {
        return prescriptionRepository.findByDoctorIdAndDeletedFlagFalse(doctorId).stream()
                .map(PrescriptionMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PrescriptionResponse> getPrescriptionsByStatus(PrescriptionStatus status) {
        return prescriptionRepository.findByStatusAndDeletedFlagFalse(status).stream()
                .map(PrescriptionMapper::toResponse)
                .toList();
    }

    @Transactional
    public PrescriptionResponse updatePrescription(Long id, PrescriptionRequest request) {
        Prescription prescription = getActivePrescription(id);
        if (prescription.getStatus() == PrescriptionStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify a cancelled prescription.");
        }
        if (prescription.getStatus() == PrescriptionStatus.DISPENSED) {
            throw new IllegalArgumentException("Cannot modify a dispensed prescription.");
        }
        if (!prescription.getVisit().getId().equals(request.getVisitId())) {
            throw new IllegalArgumentException("A prescription cannot be moved to a different visit.");
        }

        PrescriptionStatus requestedStatus = request.getStatus() == null ? prescription.getStatus() : request.getStatus();
        validatePrescriptionStatusTransition(prescription.getStatus(), requestedStatus);
        prescription.setStatus(requestedStatus);
        prescription.setInstructions(request.getInstructions());
        prescription.setNotes(request.getNotes());
        replaceItems(prescription, request.getItems());

        return PrescriptionMapper.toResponse(prescriptionRepository.save(prescription));
    }

    @Transactional
    public PrescriptionResponse deletePrescription(Long id) {
        Prescription prescription = getActivePrescription(id);
        prescription.setDeletedFlag(true);
        return PrescriptionMapper.toResponse(prescriptionRepository.save(prescription));
    }

    private Prescription getActivePrescription(Long id) {
        return prescriptionRepository.findByIdAndDeletedFlagFalse(id)
                .orElseThrow(() -> new RuntimeException("Prescription with id: " + id + " could not be found"));
    }

    private void replaceItems(Prescription prescription, List<PrescriptionItemRequest> itemRequests) {
        if (itemRequests == null || itemRequests.isEmpty()) {
            throw new IllegalArgumentException("Prescription must contain at least one item.");
        }

        prescription.getItems().clear();
        itemRequests.forEach(itemRequest -> {
            Medication medication = medicationRepository.findByIdAndDeletedFlagFalse(itemRequest.getMedicationId())
                    .orElseThrow(() -> new RuntimeException(
                            "Medication with id: " + itemRequest.getMedicationId() + " could not be found"));
            if (!Boolean.TRUE.equals(medication.getActiveFlag())) {
                throw new IllegalArgumentException("Medication with id: " + medication.getId() + " is inactive.");
            }

            PrescriptionItem item = PrescriptionItemMapper.fromRequest(itemRequest);
            item.setPrescription(prescription);
            item.setMedication(medication);
            item.setDeletedFlag(false);
            prescription.getItems().add(item);
        });
    }

    private void validateVisitCanReceivePrescription(Visit visit) {
        if (visit.getVisitStatus() == VisitStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot create a prescription for a cancelled visit.");
        }
    }

    private void validatePrescriptionStatusTransition(PrescriptionStatus currentStatus, PrescriptionStatus requestedStatus) {
        if (currentStatus == requestedStatus) {
            return;
        }

        boolean allowed = switch (currentStatus) {
            case DRAFT -> requestedStatus == PrescriptionStatus.ISSUED
                    || requestedStatus == PrescriptionStatus.CANCELLED;
            case ISSUED -> requestedStatus == PrescriptionStatus.DISPENSED
                    || requestedStatus == PrescriptionStatus.CANCELLED;
            case DISPENSED, CANCELLED -> false;
        };

        if (!allowed) {
            throw new IllegalArgumentException(
                    "Cannot move prescription status from " + currentStatus + " to " + requestedStatus + ".");
        }
    }
}
