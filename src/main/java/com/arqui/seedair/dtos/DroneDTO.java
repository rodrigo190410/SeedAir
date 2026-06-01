package com.arqui.seedair.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {

    private String code;
    private LocalDate acquisitionDate;
    private String serialNumber;
    private Long droneModelId;
}