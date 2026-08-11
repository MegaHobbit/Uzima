package com.uzima.services;

import com.uzima.Mapper.DoctorMapper;
import com.uzima.dtos.DoctorRequest;
import com.uzima.dtos.DoctorResponse;
import com.uzima.models.Doctor;
import com.uzima.models.ServicePoint;
import com.uzima.repository.DoctorRepository;
import com.uzima.repository.ServicePointRepository;
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
    private final ServicePointRepository servicePointRepository;


    public ResponseEntity<List<DoctorResponse>> postNewDoctors(List<DoctorRequest> doctorRequests) {

        List<Doctor> doctors = doctorRequests.stream()
                .map
                        (doctorRequest ->
                                {
                                    Doctor doctor = DoctorMapper.fromRequest(doctorRequest);

                                    ServicePoint servicePoint = servicePointRepository
                                            .findById(doctorRequest.getServicePointId())
                                            .orElseThrow(() ->
                                                    new RuntimeException("Service Point not found"));

                                    doctor.setServicePoint(servicePoint);

                                    return doctor;
                                }
                        )
                .toList();

        List<Doctor> savedDoctors = doctorRepository.saveAll(doctors);

        log.info("The Doctors have been saved!!!");

        List<DoctorResponse> savedDoctorsData = savedDoctors.stream()
                .map(DoctorMapper::toResponse)
                .toList();

        return ResponseEntity.ok(savedDoctorsData);

    }

    public DoctorResponse getDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));

        return DoctorMapper.toResponse(doctor);
    }

    public ResponseEntity<List<DoctorResponse>> getAllDoctor() {

        List<Doctor> doctors = doctorRepository.findAll();

        List<DoctorResponse> doctorsList = doctors.stream()
                .map(DoctorMapper::toResponse)
                .toList();

        return ResponseEntity.ok(doctorsList);
    }

    public DoctorResponse updateThisDoctor(Long id, DoctorRequest request) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));

        doctor.setFirstName(request.getFirstName());
        doctor.setLastName(request.getLastName());
        doctor.setDoctorNumber(request.getDoctorNumber());
        doctor.setPhoneNumber(request.getPhoneNumber());


        doctor.setServicePoint(servicePointRepository.findById(request.getServicePointId())
                .orElseThrow(() -> new RuntimeException("Service Point not found"))

        );

        log.info("Doctor with id: {} is updated!!!", id);
        return DoctorMapper.toResponse(doctorRepository.save(doctor));


    }

    public void deleteThisDoctor(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor with id: " + id + " could not be found"));


        doctor.setDeletedFlag(true);
        doctorRepository.save(doctor);

        log.info("Doctor with id: {} is deleted!!!", id);

    }


}
