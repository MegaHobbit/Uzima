package com.Uzima.services;

import com.Uzima.dtos.PatientData;
import com.Uzima.models.Patient;
import com.Uzima.repository.PatientRepository;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientData updatePatient(Long id, PatientData patientData) {


        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient with id: "+ id + " could not be found"));


        patient.setPatientNumber(patientData.getPatientNumber());
        patient.setPhoneNumber(patientData.getPhoneNumber());
        patient.setFirstName(patientData.getFirstName());
        patient.setLastName(patientData.getLastName());
        patient.setEmail(patientData.getEmail());
        patient.setGender(patientData.getGender());

        patientRepository.save(patient);

        log.info("Patient "+ patient.getFirstName() + " updated Successfully");

        return PatientData.toData(patient);
    }

    public PatientData createPatient(PatientData patientData) {

        Patient patient = PatientData.fromData(patientData);

        Patient savedPatient = patientRepository.save(patient);

        log.info("Patient "+ savedPatient.getFirstName() + " created Successfully");

        return PatientData.toData(savedPatient);
    }

    public PatientData getPatient(Long id) {

        Patient gottenPatient = patientRepository.findById(id)
                    .orElseThrow(() ->
                            new RuntimeException("Patient with id: "+ id + " could not be found"));

            return PatientData.toData(gottenPatient);

    }

    public String deletePatient(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Patient with id: "+ id + " could not be found"));

        patientRepository.delete(patient);
        log.info("Patient "+ patient.getFirstName() + " deleted Successfully");

        return "Patient "+ patient.getFirstName() + " has been deleted";
    }

    public ResponseEntity<List<PatientData>> getAllPatients() {

        List <Patient> patients = patientRepository.findAll();

        List<PatientData> patientList = patients.stream()
                .map(PatientData::toData)
                .toList();

        log.info("Patients found successfully");

        return ResponseEntity.ok(patientList);
    }

}
