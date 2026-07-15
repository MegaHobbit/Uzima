package com.uzima.services;

import com.uzima.dtos.DoctorData;
import com.uzima.models.Doctor;
import com.uzima.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;


    public ResponseEntity<List<DoctorData>> postNewDoctors(List<DoctorData> doctorData) {

        List<Doctor> doctors = doctorData.stream()
                .map(DoctorData::fromData)
                .toList();

        List<Doctor> savedDoctors = doctorRepository.saveAll(doctors);

        log.info("The Doctors have been saved!!!");

        List<DoctorData> savedDoctorsData = savedDoctors.stream()
                .map(DoctorData::toData)
                .toList();

        return ResponseEntity.ok(savedDoctorsData);

    }

    public DoctorData getDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));

        return DoctorData.toData(doctor);
    }

    public ResponseEntity<List<DoctorData>> getAllDoctor() {

        List<Doctor> doctors = doctorRepository.findAll();

        List<DoctorData> doctorsList = doctors.stream()
                .map(DoctorData::toData)
                .toList();

        return ResponseEntity.ok(doctorsList);
    }

    public DoctorData updateThisDoctor(Long id, DoctorData doctorData) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));

        doctor.setFirstName(doctorData.getFirstName());
        doctor.setLastName(doctorData.getLastName());
        doctor.setDoctorNumber(doctorData.getDoctorNumber());
        doctor.setPhoneNumber(doctorData.getPhoneNumber());

        log.info("Doctor with id: {} is updated!!!", id);
        return DoctorData.toData(doctorRepository.save(doctor));


    }

    public void deleteThisDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));


        doctor.setDeletedFlag(true);
        doctorRepository.save(doctor);

        log.info("Doctor with id: {} is deleted!!!", id);

    }


}
