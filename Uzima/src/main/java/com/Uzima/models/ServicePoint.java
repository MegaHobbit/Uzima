package com.Uzima.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "service_point")
public class ServicePoint extends Auditable{

    @Column(name = "point_name")
    private String pointName;

    @Column(name = "deleted_flag")
    private Boolean deletedFlag = false;

}


