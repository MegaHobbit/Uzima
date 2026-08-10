package com.uzima.services;

import com.uzima.dtos.PatientData;
import com.uzima.dtos.PatientDetails;
import com.uzima.dtos.PatientRequest;
import com.uzima.models.Doctor;
import com.uzima.models.Patient;
import com.uzima.models.ServicePoint;
import com.uzima.repository.DoctorRepository;
import com.uzima.repository.PatientRepository;
import com.uzima.repository.ServicePointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ServicePointRepository servicePointRepository;

    public PatientData updatePatient(Long id, PatientRequest patientRequest) {


        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient with id: " + id + " could not be found"));


        patient.setPatientNumber(patientRequest.getPatientNumber());
        patient.setPhoneNumber(patientRequest.getPhoneNumber());
        patient.setFirstName(patientRequest.getFirstName());
        patient.setLastName(patientRequest.getLastName());
        patient.setEmail(patientRequest.getEmail());
        patient.setGender(patientRequest.getGender());

        patient.setDoctor(doctorRepository.findById(patientRequest.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + patientRequest.getDoctorId() + " could not be found")));

        patientRepository.save(patient);

        log.info("Patient " + patient.getFirstName() + " updated Successfully");

        return PatientData.toData(patient);
    }

    public ResponseEntity<List<PatientData>> createPatient(List<PatientRequest> patientRequests) {

        List<Patient> patientsList = patientRequests.stream()
                .map(patientRequest ->
                        {
                            Patient patient = PatientRequest.fromRequest(patientRequest);

                            Doctor doctor = doctorRepository.findById(patientRequest.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor with id: " + patientRequest.getDoctorId() + " could not be found"));

                            patient.setDoctor(doctor);

                            return patient;
                        }
                )
                .toList();

        List<Patient> savedPatients = patientRepository.saveAll(patientsList);

        List<PatientData> savedPatientData = savedPatients.stream().map(PatientData::toData).toList();

        log.info("Patients created Successfully");

        return ResponseEntity.ok(savedPatientData);
    }

    public PatientData getPatient(Long id) {

        Patient gottenPatient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient with id: " + id + " could not be found"));

        return PatientData.toData(gottenPatient);

    }


    public String deletePatient(Long id) {

        Patient patient = patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient with id: " + id + " could not be found"));

        patient.setIsDeleted(true);
        patientRepository.save(patient);

        log.info("Patient " + patient.getFirstName() + " deleted Successfully");

        return "Patient " + patient.getFirstName() + " has been deleted";
    }

    public ResponseEntity<List<PatientData>> getAllPatients() {

        List<Patient> patients = patientRepository.findAll();

        List<PatientData> patientList = patients.stream().map(PatientData::toData).toList();

        log.info("Patients found successfully");

        return ResponseEntity.ok(patientList);
    }

    public PatientDetails getGottenPatientDetails(Long id) {

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient with id: " + id + " could not be found"));

        Doctor doctor = doctorRepository.findById(patient.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + patient.getDoctor().getId() + " could not be found"));

        ServicePoint servicePoint = servicePointRepository.findById(doctor.getServicePoint().getId())
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + patient.getDoctor().getId() + " could not be found"));


        PatientDetails gottenPatientDetails = PatientDetails.toData(patient, doctor, servicePoint);

        return gottenPatientDetails;
    }
}
