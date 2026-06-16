package com.Uzima.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass


public abstract class BluePrint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name= "created_on")
    private LocalDateTime createdOn;

    @Column(name= "Modified_on")
    private LocalDateTime modifiedOn;

    @Column(name= "created_by")
    private String createdBy;


}
