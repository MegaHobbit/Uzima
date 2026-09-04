package com.uzima.repository;

import com.uzima.enums.BillingStatus;
import com.uzima.models.Billing;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {

    @EntityGraph(attributePaths = "items")
    Optional<Billing> findByIdAndDeletedFlagFalse(Long id);

    @EntityGraph(attributePaths = "items")
    List<Billing> findAllByDeletedFlagFalse();

    @EntityGraph(attributePaths = "items")
    List<Billing> findByVisitIdAndDeletedFlagFalse(Long visitId);

    @EntityGraph(attributePaths = "items")
    List<Billing> findByPatientIdAndDeletedFlagFalse(Long patientId);

    @EntityGraph(attributePaths = "items")
    List<Billing> findByStatusAndDeletedFlagFalse(BillingStatus status);
}
