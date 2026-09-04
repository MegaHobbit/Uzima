package com.uzima.repository;

import com.uzima.models.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    Optional<Consultation> findByIdAndDeletedFlagFalse(Long id);

    List<Consultation> findAllByDeletedFlagFalse();

    Optional<Consultation> findByVisitIdAndDeletedFlagFalse(Long visitId);

    boolean existsByVisitIdAndDeletedFlagFalse(Long visitId);

    List<Consultation> findByDoctorIdAndDeletedFlagFalse(Long doctorId);
}
