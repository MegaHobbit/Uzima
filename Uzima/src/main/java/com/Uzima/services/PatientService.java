package com.Uzima.services;

import com.Uzima.dtos.PatientData;
import com.Uzima.models.Patient;
import com.Uzima.repository.PatientRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@RequiredArgsConstructor

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientData createPatient(PatientData patientData) {

        Patient patient = patientData.fromData(patientData);

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient "+ savedPatient.getFirstName() + " created Successfully");

        return patientData.toData(savedPatient);
    }

}
