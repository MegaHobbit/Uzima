package com.uzima.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medication")
@Getter
@Setter
@NoArgsConstructor
public class Medication extends Auditable {

    @Column(name = "medication_name", nullable = false, length = 100)
    private String medicationName;

    @Column(name = "generic_name", length = 100)
    private String genericName;

    @Column(name = "dosage_form", length = 50)
    private String dosageForm;

    @Column(name = "strength", length = 50)
    private String strength;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "active_flag", nullable = false)
    private Boolean activeFlag = true;

    @Column(name = "deleted_flag", nullable = false)
    private Boolean deletedFlag = false;
}
