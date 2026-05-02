package com.arqui.seedair.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String serialNumber;
    private LocalDate acquisitionDate;
    private String currentStatus;

    //->reservation
    @JsonIgnore
    @OneToMany(mappedBy = "drone", fetch = FetchType.EAGER)
    private List<Reservation>reservations;
    //->maintenance
    @JsonIgnore
    @OneToMany(mappedBy = "drone", fetch = FetchType.EAGER)
    private List<Maintenance> maintenances;
    //<-droneModel
    @ManyToOne
    @JoinColumn(name = "droneModel_id")
    private DroneModel droneModel;
}