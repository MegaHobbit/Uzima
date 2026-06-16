package com.Uzima.Model;
import com.Uzima.enums.VisitStatus;
import com.Uzima.enums.VisitType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patient")

public class Patient extends Person{

    private LocalDateTime visitDate;
    private String visitNumber;
    private VisitStatus visitStatus;
    private VisitType visitType;

    @ManyToOne
    @JoinColumn(name = "service_point_id")
    private ServicePoint servicePoint;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctors doctor;

    @OneToMany(mappedBy = "billing")
    private List<Billing> billing = new ArrayList<>();

}
