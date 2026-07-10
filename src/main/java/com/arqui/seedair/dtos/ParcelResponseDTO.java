package com.arqui.seedair.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelResponseDTO {
    private Long id;
    private String locationText;
    private Double totalHectares;
    private Double latitude;
    private Double longitude;
    private Double latitude2;
    private Double longitude2;
    private LocalDate createdAt;
    private String customerName;
    private Boolean isActive;
}
