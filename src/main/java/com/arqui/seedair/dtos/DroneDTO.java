package com.arqui.seedair.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DroneDTO {

    private String serialNumber;
    private Long droneModelId; // ID para vincular el dron a su modelo
}
