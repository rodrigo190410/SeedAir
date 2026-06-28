package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResponseDTO {
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean isFinished;
    private String description;
    private Double cost;
    private Long droneId;
}
