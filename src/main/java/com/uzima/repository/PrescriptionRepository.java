package com.uzima.repository;

import com.uzima.enums.PrescriptionStatus;
import com.uzima.models.Prescription;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    @EntityGraph(attributePaths = {"items", "items.medication"})
    Optional<Prescription> findByIdAndDeletedFlagFalse(Long id);

    @EntityGraph(attributePaths = {"items", "items.medication"})
    List<Prescription> findAllByDeletedFlagFalse();

    @EntityGraph(attributePaths = {"items", "items.medication"})
    List<Prescription> findByVisitIdAndDeletedFlagFalse(Long visitId);

    @EntityGraph(attributePaths = {"items", "items.medication"})
    List<Prescription> findByPatientIdAndDeletedFlagFalse(Long patientId);

    @EntityGraph(attributePaths = {"items", "items.medication"})
    List<Prescription> findByDoctorIdAndDeletedFlagFalse(Long doctorId);

    @EntityGraph(attributePaths = {"items", "items.medication"})
    List<Prescription> findByStatusAndDeletedFlagFalse(PrescriptionStatus status);
}
