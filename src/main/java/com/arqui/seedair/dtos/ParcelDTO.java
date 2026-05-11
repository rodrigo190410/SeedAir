package com.arqui.seedair.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelDTO {
    private String locationText;
    private Double totalHectares;
    private Double latitude;
    private Double longitude;
}
