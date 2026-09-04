package com.uzima.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultation")
@Getter
@Setter
@NoArgsConstructor
public class Consultation extends Auditable {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visit_id", nullable = false, unique = true)
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "consultation_date_time", nullable = false)
    private LocalDateTime consultationDateTime;

    @Column(name = "chief_complaint", length = 500)
    private String chiefComplaint;

    @Column(name = "history_of_present_illness", length = 1000)
    private String historyOfPresentIllness;

    @Column(name = "examination_findings", length = 1000)
    private String examinationFindings;

    @Column(name = "assessment", length = 1000)
    private String assessment;

    @Column(name = "diagnosis", length = 500)
    private String diagnosis;

    @Column(name = "treatment_plan", length = 1000)
    private String treatmentPlan;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "deleted_flag", nullable = false)
    private Boolean deletedFlag = false;
}
