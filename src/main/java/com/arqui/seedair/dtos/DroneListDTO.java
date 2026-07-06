package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneListDTO {
    private Long id;
    private String code;
    private String serialNumber;
    private LocalDate acquisitionDate;

    private String modelName;
    private String brandName;
}