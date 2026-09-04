package com.uzima.repository;

import com.uzima.enums.VisitStatus;
import com.uzima.models.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    Optional<Visit> findByIdAndDeletedFlagFalse(Long id);

    List<Visit> findAllByDeletedFlagFalse();

    List<Visit> findByPatientIdAndDeletedFlagFalse(Long patientId);

    List<Visit> findByDoctorIdAndDeletedFlagFalse(Long doctorId);

    Optional<Visit> findByAppointmentIdAndDeletedFlagFalse(Long appointmentId);

    List<Visit> findByVisitStatusAndDeletedFlagFalse(VisitStatus visitStatus);

    List<Visit> findByVisitDateTimeBetweenAndDeletedFlagFalse(LocalDateTime start, LocalDateTime end);
}
