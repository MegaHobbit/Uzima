package com.uzima.models;

import com.uzima.enums.ClinicalReportType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "clinical_report")
@Getter
@Setter
@NoArgsConstructor
public class ClinicalReport extends Auditable {

    @Column(name = "report_number", nullable = false, unique = true, length = 50)
    private String reportNumber;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false, length = 50)
    private ClinicalReportType reportType;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "findings", length = 2000)
    private String findings;

    @Column(name = "conclusion", length = 1000)
    private String conclusion;

    @Column(name = "recommendations", length = 1000)
    private String recommendations;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "deleted_flag", nullable = false)
    private Boolean deletedFlag = false;
}
