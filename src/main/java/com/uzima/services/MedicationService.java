package com.uzima.services;

import com.uzima.Mapper.MedicationMapper;
import com.uzima.dtos.MedicationRequest;
import com.uzima.dtos.MedicationResponse;
import com.uzima.models.Medication;
import com.uzima.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository medicationRepository;

    @Transactional
    public MedicationResponse createMedication(MedicationRequest request) {
        Medication medication = MedicationMapper.fromRequest(request);
        medication.setDeletedFlag(false);
        return MedicationMapper.toResponse(medicationRepository.save(medication));
    }

    @Transactional(readOnly = true)
    public MedicationResponse getMedication(Long id) {
        return MedicationMapper.toResponse(getActiveMedication(id));
    }

    @Transactional(readOnly = true)
    public List<MedicationResponse> getAllMedications() {
        return medicationRepository.findAllByDeletedFlagFalse().stream()
                .map(MedicationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MedicationResponse> getActiveMedications() {
        return medicationRepository.findAllByActiveFlagTrueAndDeletedFlagFalse().stream()
                .map(MedicationMapper::toResponse)
                .toList();
    }

    @Transactional
    public MedicationResponse updateMedication(Long id, MedicationRequest request) {
        Medication medication = getActiveMedication(id);
        MedicationMapper.updateMedication(medication, request);
        return MedicationMapper.toResponse(medicationRepository.save(medication));
    }

    @Transactional
    public MedicationResponse deleteMedication(Long id) {
        Medication medication = getActiveMedication(id);
        medication.setDeletedFlag(true);
        medication.setActiveFlag(false);
        return MedicationMapper.toResponse(medicationRepository.save(medication));
    }

    Medication getActiveMedication(Long id) {
        return medicationRepository.findByIdAndDeletedFlagFalse(id)
                .orElseThrow(() -> new RuntimeException("Medication with id: " + id + " could not be found"));
    }
}
