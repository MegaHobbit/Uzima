package com.Uzima.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employees")

public class Employees extends Person {

    private  String employeeNumber;
    private String employeeAccNumber;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}