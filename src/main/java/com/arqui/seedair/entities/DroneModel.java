package com.arqui.seedair.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "drones_models")
public class DroneModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String brand;
    private String modelName;
    private Double seedCapacityKg;
    private Double coverageHectaresPerDay;
    private Integer autonomyMinutes;
    private Double maxSpeedKmh;
    //->Drone
    @JsonIgnore
    @OneToMany(mappedBy = "droneModel", fetch = FetchType.EAGER)
    private List<Drone> drones;
}
