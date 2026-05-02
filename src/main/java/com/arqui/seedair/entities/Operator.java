package com.arqui.seedair.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operators")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licenseCode;
    private String certificationLevel;
    private Integer experienceYears;
    private String availabilityStatus;
    //->reservation
    @OneToMany(mappedBy = "operator", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

}
