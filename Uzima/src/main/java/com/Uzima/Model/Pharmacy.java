package com.Uzima.Model;
import com.Uzima.enums.PharmStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pharmacy")

public class Pharmacy extends BluePrint{

    private PharmStatus status;
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Items item;

    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL)
    private List<Billing> billing = new ArrayList<>();

}