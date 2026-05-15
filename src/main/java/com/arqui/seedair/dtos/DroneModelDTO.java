package com.arqui.seedair.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneModelDTO {

    private String brand;
    private String modelName;
    private Double seedCapacityKg;
    private Double coverageHectaresPerDay;
    private Integer autonomyMinutes;
    private Double maxSpeedKmh;
}
