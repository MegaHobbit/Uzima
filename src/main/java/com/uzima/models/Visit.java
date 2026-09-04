package com.uzima.models;

import com.uzima.enums.VisitStatus;
import com.uzima.enums.VisitType;
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
@Table(name = "visit")
@Getter
@Setter
@NoArgsConstructor
public class Visit extends Auditable {

    @Column(name = "visit_number", nullable = false, unique = true, length = 50)
    private String visitNumber;

    @Column(name = "visit_date_time", nullable = false)
    private LocalDateTime visitDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type", nullable = false, length = 50)
    private VisitType visitType;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_status", nullable = false, length = 50)
    private VisitStatus visitStatus;

    @Column(name = "chief_complaint", length = 500)
    private String chiefComplaint;

    @Column(name = "notes", length = 1000)
    private String notes;

    @Column(name = "deleted_flag", nullable = false)
    private Boolean deletedFlag = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "service_point_id", nullable = false)
    private ServicePoint servicePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", unique = true)
    private Appointment appointment;
}
