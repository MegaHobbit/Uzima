package com.Uzima.Model;
import com.Uzima.enums.Gender;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Person")

public class Person extends BluePrint{
    private String firstName;
    private String lastName;
    private String surName;
    private LocalDateTime dateOfBirth;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private String residence;

}