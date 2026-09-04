package com.uzima.repository;

import com.uzima.models.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    Optional<Medication> findByIdAndDeletedFlagFalse(Long id);

    List<Medication> findAllByDeletedFlagFalse();

    List<Medication> findAllByActiveFlagTrueAndDeletedFlagFalse();
}
