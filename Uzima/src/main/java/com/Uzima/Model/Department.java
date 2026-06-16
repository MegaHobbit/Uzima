package com.Uzima.Model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "department")

public class Department extends BluePrint {

    private String depId;
    private String depName;
    private String depDescription;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employees> employees = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Items> items = new ArrayList<>();
}
