package com.Uzima.Model;

import com.Uzima.enums.TestStatus;
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
@Table(name = "lab_tests")

public class LabTests extends BluePrint{

    private Long testId;
    private String description;
    private String category;
    private BigDecimal amount;
    private Long quantity;
    private TestStatus status;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private Lab lab;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Items item;

}
