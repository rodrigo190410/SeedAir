package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneModelListDTO {
    private Long id;
    private String brandName;
    private String modelName;
    private Double seedCapacityKg;
    private Double coverageHectaresPerDay;
    private Integer autonomyMinutes;
    private Double maxSpeedKmh;

}
