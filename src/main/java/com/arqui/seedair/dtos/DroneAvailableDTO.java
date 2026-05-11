package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneAvailableDTO {
    private String modelName;
    private String brand;
    private Double seedCapacityKg; // Capacidad de semillas
    private Integer autonomyMinutes; // Tiempo de vuelo
    //private String status;
}
