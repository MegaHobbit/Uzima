package com.uzima.repository;

import com.uzima.enums.ClinicalReportType;
import com.uzima.models.ClinicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClinicalReportRepository extends JpaRepository<ClinicalReport, Long> {

    Optional<ClinicalReport> findByIdAndDeletedFlagFalse(Long id);

    List<ClinicalReport> findAllByDeletedFlagFalse();

    List<ClinicalReport> findByVisitIdAndDeletedFlagFalse(Long visitId);

    List<ClinicalReport> findByPatientIdAndDeletedFlagFalse(Long patientId);

    List<ClinicalReport> findByDoctorIdAndDeletedFlagFalse(Long doctorId);

    List<ClinicalReport> findByReportTypeAndDeletedFlagFalse(ClinicalReportType reportType);
}
