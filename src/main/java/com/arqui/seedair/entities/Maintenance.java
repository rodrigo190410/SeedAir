package com.arqui.seedair.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "maintenances")
public class Maintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String description;
    private Double cost;
    private LocalDate createdAt;
    //<-drone
    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;
}
