package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceRegisterDTO {
    private LocalDate startDate;
    private String description;
    private Double cost;
    private Long droneId;
}
