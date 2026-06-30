package com.Uzima.repository;

import com.Uzima.dtos.PatientData;
import com.Uzima.models.Patient;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

}
