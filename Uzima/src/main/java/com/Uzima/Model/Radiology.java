package com.Uzima.Model;
import com.Uzima.enums.RadStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "radiology")

public class Radiology extends BluePrint {

    private String radiologyNumber;
    private BigDecimal price;
    private String description;
    private String category;
    private BigDecimal amount;
    private RadStatus radStatus;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Items item;

}